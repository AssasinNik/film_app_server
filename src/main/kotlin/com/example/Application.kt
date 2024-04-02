package com.example

import com.example.data.DataBase
import com.example.plugins.*
import com.example.repository.UserRepository
import com.example.repository.UserRepositoryImpl
import com.example.routes.authRoutes
import com.example.service.UserService
import com.example.service.UserServiceImpl
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DataBase.init()
    install(ContentNegotiation) {
        jackson()
    }
    val service: UserService=UserServiceImpl()
    val repository: UserRepository=UserRepositoryImpl(service)
    authRoutes(repository)
    configureRouting()
}
