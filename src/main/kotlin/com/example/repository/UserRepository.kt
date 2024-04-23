package com.example.repository

import com.example.service_user.CreateUserParams
import com.example.service_user.ImageParams
import com.example.service_user.LoginUserParams
import com.example.service_user.TokenParams
import com.example.utils.Response

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): Response<Any>
    suspend fun loginUser(params: LoginUserParams): Response<Any>
    suspend fun InfoByToken(params: TokenParams): Response<Any>
    suspend fun ChangePassword(params: LoginUserParams): Response<Any>
    suspend fun ChangeImage(params: ImageParams): Response<Any>
}