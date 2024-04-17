package com.example.service

import com.example.user.UserDTO

interface UserService {
    suspend fun registerUser(params: CreateUserParams): UserDTO?
    suspend fun findByEmail(email:String):UserDTO?
    suspend fun findUser(email: String, password: String):UserDTO?
    suspend fun findByToken(token: String):UserDTO?
}