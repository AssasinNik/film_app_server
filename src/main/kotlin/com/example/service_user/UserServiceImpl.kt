package com.example.service_user

import com.example.data.DataBase.dbQuery
import com.example.secure.JWTauth
import com.example.secure.hash
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
            Users.select(Users.email.eq(email))
                .map { rowToUser(it) }.singleOrNull()
        }
        return user
    }
    override suspend fun findUser(email: String, password: String): UserDTO? {
        val user= dbQuery {
            Users.select(Users.email.eq(email) and Users.parol_user.eq(hash(password)))
                .map { rowToUser(it) }.singleOrNull()
        }
        return user
    }

    override suspend fun findByToken(token: String): UserDTO? {
        val decode: String = JWTauth.instance.decodeToken(token, "back-to-the-future")
        val user= dbQuery {
            Users.select(Users.email.eq(decode))
                .map { rowToUser(it) }.singleOrNull()
        }
        return user
    }

    override suspend fun change_image(email: String, image: String): Boolean {
        val user= findByEmail(email)
        if(user!=null){
            dbQuery {
                Users.update({ Users.email eq email }) {
                    it[Users.image]=image
                }
            }
            return true
        }
        else{
            return false
        }
    }

    override suspend fun change_password(email: String, password: String, new_password: String?): Boolean {
        val user= findUser(email, password)
        if(user!=null){
            dbQuery {
                Users.update({ Users.email eq email }) {
                    it[Users.parol_user]=hash(new_password)
                }
            }
            return true
        }
        else{
            return false
        }
    }

    private fun rowToUser(row: ResultRow?): UserDTO? {
        return if(row == null) {
            null
        } else UserDTO(
            id= row[Users.id],
            email= row[Users.email],
            username = row[Users.username],
            image = row[Users.image],
        )
    }
}