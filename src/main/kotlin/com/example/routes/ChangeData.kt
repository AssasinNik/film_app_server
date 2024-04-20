package com.example.routes

import com.example.repository.UserRepository
import com.example.service.ImageParams
import com.example.service.LoginUserParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.changeData(repository: UserRepository){
    routing {
        route("/change"){
            post("/password"){
                val params = call.receive<LoginUserParams>()
                val result = repository.ChangePassword(params)
                call.respond(result.statusCode, result)
            }
            post("/image"){
                val params =call.receive<ImageParams>()
                val result = repository.ChangeImage(params)
                call.respond(result.statusCode, result)
            }
        }
    }
}