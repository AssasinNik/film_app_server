package com.example.repository

import com.example.secure.JWTauth
import com.example.service.CreateUserParams
import com.example.service.LoginUserParams
import com.example.service.UserService
import com.example.utils.Response

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun registerUser(params: CreateUserParams): Response<Any>{
        return if(isEmailExist(params.email)){
            Response.ErrorResponse(message = "Email already exists")
        }else{
            val user=userService.registerUser(params)
            if(user != null){
                Response.SuccessResponse(data = user)
            }else{
                Response.ErrorResponse()
            }
        }
    }

    override suspend fun loginUser(params: LoginUserParams): Response<Any> {
        val user= userService.findUser(params.email, params.parol_user)
        return if(user!=null){
            Response.SuccessResponse(data=user)
        }
        else{
            Response.ErrorResponse(message="User does not exist")
        }
    }

    private suspend fun isEmailExist(email: String): Boolean{
        return userService.findByEmail(email)!=null
    }
}