package com.example.repository

import com.example.secure.JWTauth
import com.example.service_user.*
import com.example.utils.Response
import io.ktor.http.content.*
import java.io.File
import java.util.*

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun registerUser(params: CreateUserParams): Response<Any>{
        return if(isEmailExist(params.email)){
            Response.ErrorResponse(message = "Email already exists")
        }else{
            val user=userService.registerUser(params)
            if(user != null){
                val token = JWTauth.instance.createToken(user.email)
                user.token = token
                userService.registerToken(token)
                Response.SuccessResponse(data = user)
            }else{
                Response.ErrorResponse()
            }
        }
    }

    override suspend fun loginUser(params: LoginUserParams): Response<Any> {
        val user= userService.findUser(params.email, params.parol_user)
        return if(isEmailExist(params.email)){
            if(user!=null){
                val token = JWTauth.instance.createToken(user.email)
                user.token = token
                Response.SuccessResponse(data = user)
            }
            else{
                Response.ErrorResponse(message = "Invalid password")
            }
        }
        else{
            Response.ErrorResponse(message="User does not exist")
        }
    }

    override suspend fun InfoByToken(params: TokenParams): Response<Any> {
        val user= userService.findByToken(params.token)
        return if(user!=null){
            Response.SuccessResponse(data = user)
        }
        else{
            Response.ErrorResponse(message="User does not exist")
        }
    }

    override suspend fun ChangeImage(multipart: MultiPartData): Response<Any> {
        var name = "";
        var token: String = ""
        multipart.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name == "token") {
                        token = part.value
                    }
                }

                is PartData.FileItem -> {
                    val originalFileName = part.originalFileName
                    val extension = originalFileName?.substringAfterLast('.', "")
                    val fileName = "${UUID.randomUUID()}.$extension"
                    val fileBytes = part.streamProvider().readBytes()
                    name = fileName
                    val file = File("uploads/$fileName")
                    file.parentFile.mkdirs()
                    file.writeBytes(fileBytes)
                    part.dispose()
                }

                else -> {
                    part.dispose()
                }
            }
        }
        val user = userService.findByToken(token)
        return if(user != null){
            if(user.image != null){
                val prevFile = File("uploads/${user.image}")
                prevFile.delete()
            }
            val change = userService.change_image(user.email, name)
            if(change){
                Response.SuccessResponse(data = name, message = "Image has been saved")
            } else{
                Response.ErrorResponse(message = "Some problems")
            }
        } else{
            Response.ErrorResponse(message = "No user")
        }
    }
    override suspend fun ChangePassword(params: PasswordParams): Response<Any> {
        val result=userService.change_password(params.email, params.parol_user, params.new_parol, params.token)
        return if(result){
            val token = JWTauth.instance.createToken(params.email)
            userService.registerToken(token)
            Response.SuccessResponse(message = "Update completed")
        }
        else{
            Response.ErrorResponse(message="Some problem")
        }
    }

    private suspend fun isEmailExist(email: String): Boolean{
        return userService.findByEmail(email)!=null
    }
}