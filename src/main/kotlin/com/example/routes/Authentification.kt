package com.example.routes

import com.example.repository.UserRepository
import com.example.service_user.CreateUserParams
import com.example.service_user.LoginUserParams
import com.example.service_user.TokenParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoutes(repository: UserRepository){
    routing {
        route("/auth"){
            post("/register"){
                val params = call.receive<CreateUserParams>()
                val result = repository.registerUser(params)
                call.respond(result.statusCode, result)
            }
            post("/login"){
                val params =call.receive<LoginUserParams>()
                val result = repository.loginUser(params)
                call.respond(result.statusCode, result)
            }
            post("/authenticate"){
                val params = call.receive<TokenParams>()
                val result = repository.InfoByToken(params)
                call.respond(result.statusCode, result)
            }
        }
    }
}