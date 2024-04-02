package com.example.repository

import com.example.service.CreateUserParams
import com.example.utils.Response

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): Response<Any>
    suspend fun loginUser(email: String, password: String): Response<Any>
}