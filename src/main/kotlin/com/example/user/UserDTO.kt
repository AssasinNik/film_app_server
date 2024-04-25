package com.example.user

data class UserDTO(
    val id: Int,
    val username: String?,
    val email: String,
    val parol: String?=null,
    var token: String?=null,
    val image: String?
)