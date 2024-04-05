package com.example.secure

import io.ktor.server.auth.*

data class UserIdPrincipalForUser(val id: Int) : Principal