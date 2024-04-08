package com.example.service

data class CreateUserParams(
    val id: Int,
    val email: String,
    val username: String,
    val parol_user: String,
    val image: String?
)
