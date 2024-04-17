package com.example.repository

import com.example.service.CreateUserParams
import com.example.service.LoginUserParams
import com.example.service.TokenParams
import com.example.utils.Response

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): Response<Any>
    suspend fun loginUser(params: LoginUserParams): Response<Any>
    suspend fun InfoByToken(params: TokenParams): Response<Any>
}