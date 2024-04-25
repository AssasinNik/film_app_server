package com.example.service_user

import com.example.data.DataBase.dbQuery
import com.example.secure.JWTauth
import com.example.secure.hash
import com.example.secure.verifyPassword
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
        return rowToUser(statement?.resultedValues?.get(0))
    }

    override suspend fun findByEmail(email: String): UserDTO? {
        val user= dbQuery {
            Users.selectAll().where(Users.email.eq(email))
                .map { rowToUser(it) }.singleOrNull()
        }
        return user
    }
    override suspend fun findByEmailwithParol(email: String): UserDTO? {
        val user= dbQuery {
            Users.selectAll().where(Users.email.eq(email))
                .map { allrowToUser(it) }.singleOrNull()
        }
        return user
    }
    override suspend fun findUser(email: String, password: String): UserDTO? {
        val first=findByEmailwithParol(email)
        return if (first != null) {
            if(verifyPassword(password, first.parol)){
                val user= dbQuery {
                    Users.selectAll().where(Users.email.eq(email))
                        .map { rowToUser(it) }.singleOrNull()
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
        val decode: String = JWTauth.instance.decodeToken(token, "back-to-the-future")
        val user= dbQuery {
            Users.selectAll().where(Users.email.eq(decode))
                .map { rowToUser(it) }.singleOrNull()
        }
        return user
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

    override suspend fun change_password(email: String, password: String, new_password: String?): Boolean {
        val user= findUser(email, password)
        return if(user!=null){
            dbQuery {
                Users.update({ Users.email eq email }) {
                    it[Users.parol_user]=hash(new_password)
                }
            }
            true
        } else{
            false
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