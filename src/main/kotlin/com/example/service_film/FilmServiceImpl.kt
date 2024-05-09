package com.example.service_film

import com.example.data.DataBase
import com.example.films.Film
import com.example.films.FilmDTO
import com.example.secure.JWTauth
import com.example.user.Tokens
import com.example.user.UserDTO
import com.example.user.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq

class FilmServiceImpl : FilmService {
    override suspend fun findByToken(token: String): UserDTO? {

        return if (DataBase.dbQuery {
                Tokens.selectAll().where { Tokens.token eq token }
                    .count() > 0
            }){
            val decode: String = JWTauth.instance.decodeToken(token, "back-to-the-future")
            val user= DataBase.dbQuery {
                Users.selectAll().where { Users.email.eq(decode) }
                    .map { rowToUser(it) }.singleOrNull()
            }
            user
        } else{
            null
        }
    }

    override suspend fun findNewFilm(): List<FilmDTO> {
        val date: Int = 2024
        val rating: Float = 6.2F
        return DataBase.dbQuery {
            Film.selectAll().where((Film.releaseDate eq date) and (Film.rating_kp greaterEq rating))
                .mapNotNull { rowToFilm(it) }
        }
    }
    override suspend fun findFilmMood(genres: List<String>): List<FilmDTO> {
        return DataBase.dbQuery {
            Film.selectAll().where((Film.genres eq genres))
                .mapNotNull { rowToFilm(it) }
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
            parol = row[Users.parol_user]
        )
    }
    private fun rowToFilm(row: ResultRow?): FilmDTO? {
        return if(row == null) {
            null
        } else FilmDTO(
            movie_id = row[Film.filmid],
            title= row[Film.title],
            titleEn = row[Film.titleEN],
            genres = row[Film.genres],
            posterURL = row[Film.poster_url],
            rating = row[Film.rating_kp],
            length = row[Film.movie_length],
            description = row[Film.description],
            releaseDate = row[Film.releaseDate],
            ageLimit = row[Film.ageLimit]
        )
    }

}