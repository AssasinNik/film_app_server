package com.example.routes

import com.example.repository.FilmRepository
import com.example.repository.UserRepository
import com.example.service_user.ImageParams
import com.example.service_user.LoginUserParams
import com.example.service_user.TokenParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getFilm(repository: FilmRepository){
    routing {
        route("/films"){
            post("/movie_for_mood"){
                TODO("For mood")
            }
            post("/new"){
                val params = call.receive<TokenParams>()
                val result = repository.getNewFilm(params)
                call.respond(result.statusCode, result)
            }
        }
    }
}