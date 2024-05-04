package com.example.repository

import com.example.service_user.MoodParams
import com.example.service_user.TokenParams
import com.example.utils.Response

interface FilmRepository {

    suspend fun getNewFilm(params: TokenParams) : Response<Any>

    suspend fun getMovieMood(params: MoodParams) : Response<Any>
}