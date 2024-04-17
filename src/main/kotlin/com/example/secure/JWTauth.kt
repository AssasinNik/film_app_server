package com.example.secure

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JWTauth private constructor(secret: String) {
    private val algorithm= Algorithm.HMAC256(secret)
    val verifier : JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(Issuer)
        .withAudience(Audience)
        .build()


    fun createToken(email : String) : String = JWT
        .create()
        .withIssuer(Issuer)
        .withAudience(Audience)
        .withClaim(Claim, email)
        .sign(algorithm)
    fun decodeToken(token: String, secret: String): String {
        val algorithm = Algorithm.HMAC256(secret)
        val verifier = JWT.require(algorithm).build()
        val jwt = verifier.verify(token)
        return jwt.getClaim("email").asString()
    }

    companion object{
        private const val Issuer="film_app"
        private const val Audience="film_app"
        const val Claim="email"

        lateinit var instance: JWTauth
            private set


        fun initialize(secret: String){
            synchronized(this){
                if(!this::instance.isInitialized){
                    instance= JWTauth(secret)
                }
            }
        }

    }
}