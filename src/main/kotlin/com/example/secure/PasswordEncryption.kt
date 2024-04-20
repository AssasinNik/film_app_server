package com.example.secure

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val secret_key="89033212811"
private val algorithm="HmacSHA256"

private val hashKey= hex(secret_key)
private val hmac_key=SecretKeySpec(hashKey, algorithm)

fun hash(password :String?) : String{
    val hmac= Mac.getInstance(algorithm)
    hmac.init(hmac_key)
    return hex(hmac.doFinal(password?.toByteArray(Charsets.UTF_8)))
}