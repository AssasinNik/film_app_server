package com.example

import com.example.data.DataBase
import com.example.plugins.*
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DataBase.init()
    configureRouting()
}
