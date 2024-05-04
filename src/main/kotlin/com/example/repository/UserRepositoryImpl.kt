package com.example.repository

import com.example.secure.JWTauth
import com.example.service_user.*
import com.example.utils.Response
import io.ktor.http.content.*
import java.io.File

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun registerUser(params: CreateUserParams): Response<Any>{
        return if(isEmailExist(params.email)){
            Response.ErrorResponse(message = "Email already exists")
        }else{
            val user=userService.registerUser(params)
            if(user != null){
                val token = JWTauth.instance.createToken(user.email)
                user.token = token
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

    override suspend fun ChangeImage(params: TokenParams, multipart: MultiPartData): Response<Any> {
        val user= userService.findByToken(params.token)
        return if(user!=null){
            var name = "";
            multipart.forEachPart { part ->
                when(part){
                    is PartData.FileItem -> {
                        val fileName = part.originalFileName as String
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
            val change = userService.change_image(user.email, name)
            if(change == true){
                Response.SuccessResponse(message="File upload")
            }
            else{
                Response.ErrorResponse(message="Some problems")
            }

        }
        else{
            Response.ErrorResponse(message="Some problems")
        }
    }

    override suspend fun ChangePassword(params: LoginUserParams): Response<Any> {
        val result=userService.change_password(params.email, params.parol_user, params.new_parol)
        return if(result){
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