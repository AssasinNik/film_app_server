package com.example.service_user

data class CreateUserParams(
    val id: Int,
    val email: String,
    val username: String,
    val parol_user: String,
    val image: String?
)
