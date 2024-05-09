package com.example.service_user

data class PasswordParams (
    val email: String,
    val parol_user: String,
    val new_parol: String?,
    val token: String
)