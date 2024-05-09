package com.example.routes

import com.example.repository.UserRepository
import com.example.service_user.LoginUserParams
import com.example.service_user.PasswordParams
import com.example.service_user.TokenParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.changeData(repository: UserRepository){
    routing {
        route("/change"){
            post("/password"){
                val params = call.receive<PasswordParams>()
                val result = repository.ChangePassword(params)
                call.respond(result.statusCode, result)
            }
            post("/image"){
                val multipart = call.receiveMultipart()
                val result = repository.ChangeImage(multipart)
                call.respond(result.statusCode, result)
            }
            get("/images/{fileName}") {
                val fileName = call.parameters["fileName"] ?: throw IllegalArgumentException("Missing parameter: fileName")
                val imagePath = "uploads/$fileName"
                val imageFile = File(imagePath)
                if (imageFile.exists()) {
                    call.respond(LocalFileContent(imageFile))
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}