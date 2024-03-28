package com.example.user

import org.jetbrains.exposed.sql.Table

object Users: Table("users") {
    private val id = Users.integer("id")
    private val username = Users.varchar("username", 40)
    private val email= Users.varchar("email", 320)
    private val parol_user=Users.varchar("parol_user", 200)
    private val token=Users.varchar("token", 300)
    private val image=Users.varchar("image", 50)
}