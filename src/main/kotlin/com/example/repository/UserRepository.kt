package com.example.repository

import com.example.service_user.CreateUserParams
import com.example.service_user.LoginUserParams
import com.example.service_user.PasswordParams
import com.example.service_user.TokenParams
import com.example.utils.Response
import io.ktor.http.content.*

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams): Response<Any>
    suspend fun loginUser(params: LoginUserParams): Response<Any>
    suspend fun InfoByToken(params: TokenParams): Response<Any>
    suspend fun ChangePassword(params: PasswordParams): Response<Any>
    suspend fun ChangeImage(multipart: MultiPartData): Response<Any>
}