package com.example.repository

import com.example.service.CreateUserParams
import com.example.service.UserService
import com.example.utils.Response

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun registerUser(params: CreateUserParams): Response<Any>{
        return if(isEmailExist(params.email)){
            Response.ErrorResponse(message = "Email already exists")
        }else{
            val user=userService.registerUser(params)
            if(user != null){
                //@Todo generate token for user
                Response.SuccessResponse(data = user)
            }else{
                Response.ErrorResponse()
            }
        }
    }

    override suspend fun loginUser(email: String, password: String): Response<Any> {
        TODO("Not yet implemented")
    }

    private suspend fun isEmailExist(email: String): Boolean{
        return userService.findByEmail(email)!=null
    }
}