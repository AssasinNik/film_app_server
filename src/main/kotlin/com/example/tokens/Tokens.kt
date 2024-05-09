package com.example.user

import org.jetbrains.exposed.sql.Table

object Tokens: Table("jwt_tokens") {
    val token = Tokens.varchar("token", 255)
    override val primaryKey=PrimaryKey(token)
}