package com.example.secure

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(){
    JWTauth.initialize("back-to-the-future")
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JWTauth.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JWTauth.Claim).asInt()
                if(claim != null){
                    UserIdPrincipalForUser(claim)
                }else{
                    null
                }
            }
        }
    }
}