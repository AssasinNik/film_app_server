package com.example.service_film

import com.example.films.FilmDTO
import com.example.user.UserDTO

interface FilmService {
    suspend fun findByToken(token: String): UserDTO?
    suspend fun findNewFilm(): List<FilmDTO>
}