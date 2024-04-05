package com.example.user

data class UserDTO(
    val username: String?,
    val email: String,
    val parol_user: String,
    val token: String?=null,
    val image: String?
)