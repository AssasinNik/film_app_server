package com.example.service_user

import com.example.user.UserDTO

interface UserService {
    suspend fun registerUser(params: CreateUserParams): UserDTO?
    suspend fun findByEmail(email:String):UserDTO?
    suspend fun findUser(email: String, password: String):UserDTO?
    suspend fun findByToken(token: String):UserDTO?
    suspend fun change_image(email: String, image: String):Boolean
    suspend fun change_password(email: String, password: String, new_password: String?, token: String):Boolean
    suspend fun findByEmailwithParol(email: String):UserDTO?
    suspend fun registerToken(token: String)

}