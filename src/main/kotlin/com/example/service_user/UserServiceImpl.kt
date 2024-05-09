package com.example.service_user

import com.example.data.DataBase.dbQuery
import com.example.secure.JWTauth
import com.example.secure.hash
import com.example.secure.verifyPassword
import com.example.user.Tokens
import com.example.user.UserDTO
import com.example.user.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): UserDTO? {
        var statement: InsertStatement<Number>? = null
        dbQuery{
            statement= Users.insert{
                it[email] = params.email
                it[parol_user]=hash(params.parol_user)
                it[username]=params.username
            }
        }
        return allrowToUser(statement?.resultedValues?.get(0))
    }

    override suspend fun findByEmail(email: String): UserDTO? {

        val user= dbQuery {
            Users.selectAll().where { Users.email eq email }
                .map { allrowToUser(it) }.singleOrNull()
        }
        return user
    }
    override suspend fun findByEmailwithParol(email: String): UserDTO? {
        val user= dbQuery {
            Users.selectAll().where { Users.email.eq(email) }
                .map { allrowToUser(it) }.singleOrNull()
        }
        return user
    }
    override suspend fun findUser(email: String, password: String): UserDTO? {
        val first=findByEmailwithParol(email)
        return if (first != null) {
            if(verifyPassword(password, first.parol)){
                val user= dbQuery {
                    Users.selectAll().where { Users.email.eq(email) }
                        .map { allrowToUser(it) }.singleOrNull()
                }
                user
            } else{
                null
            }
        } else{
            null
        }
    }

    override suspend fun findByToken(token: String): UserDTO? {

        return if (dbQuery{
                Tokens.selectAll().where { Tokens.token eq token }
                .count() > 0}){
            val decode: String = JWTauth.instance.decodeToken(token, "back-to-the-future")
            val user= dbQuery {
                Users.selectAll().where { Users.email.eq(decode) }
                    .map { allrowToUser(it) }.singleOrNull()
            }
            user
        } else{
            null
        }
    }

    override suspend fun change_image(email: String, image: String): Boolean {
        val user= findByEmail(email)
        return if(user!=null){
            dbQuery {
                Users.update({ Users.email eq email }) {
                    it[Users.image]=image
                }
            }
            true
        } else{
            false
        }
    }

    override suspend fun change_password(email: String, password: String, new_password: String?, token: String): Boolean {
        val user= findUser(email, password)
        return if(user!=null){
            dbQuery {
                Users.update({ Users.email eq email }) {
                    it[parol_user]=hash(new_password)
                }
            }
            dbQuery {
                Tokens.deleteWhere{ Tokens.token eq token }
            }
            true
        } else{
            false
        }
    }

    override suspend fun registerToken(token: String) {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement=Tokens.insert {
                it[Tokens.token]=token
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserDTO? {
        return if(row == null) {
            null
        } else UserDTO(
            id= row[Users.id],
            email= row[Users.email],
            username = row[Users.username],
            image = row[Users.image]
        )
    }
    private fun allrowToUser(row: ResultRow?): UserDTO? {
        return if(row == null) {
            null
        } else UserDTO(
            id= row[Users.id],
            email= row[Users.email],
            username = row[Users.username],
            image = row[Users.image],
            parol = row[Users.parol_user]
        )
    }
}