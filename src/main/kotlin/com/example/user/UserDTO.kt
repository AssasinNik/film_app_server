package com.example.user

data class UserDTO(
    val id: Int,
    val username: String?,
    val email: String,
    var token: String?=null,
    val image: String?
)