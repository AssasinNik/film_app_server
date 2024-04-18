package com.example

import com.example.data.DataBase
import com.example.plugins.*
import com.example.repository.UserRepository
import com.example.repository.UserRepositoryImpl
import com.example.routes.authRoutes
import com.example.secure.configureSecurity
import com.example.service.UserService
import com.example.service.UserServiceImpl
import io.ktor.network.tls.certificates.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import org.slf4j.LoggerFactory
import java.io.File


fun main(args: Array<String>) {
    val keyStoreFile = File("build/keystore.jks")
    val keyStore = buildKeyStore {
        certificate("sampleAlias") {
            password = "parol1810"
            domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
        }
    }
    keyStore.saveToFile(keyStoreFile, "parol1810")

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keyStore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "parol1810".toCharArray() },
            privateKeyPassword = { "parol1810".toCharArray() }) {
            port = 8443
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DataBase.init()
    install(ContentNegotiation) {
        jackson()
    }
    configureSecurity()


    val service: UserService=UserServiceImpl()
    val repository: UserRepository=UserRepositoryImpl(service)


    authRoutes(repository)

    configureRouting()
}
