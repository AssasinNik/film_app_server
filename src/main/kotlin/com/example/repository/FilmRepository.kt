package com.example.repository

import com.example.service_user.TokenParams
import com.example.utils.Response

interface FilmRepository {

    suspend fun getNewFilm(params: TokenParams) : Response<Any>

}