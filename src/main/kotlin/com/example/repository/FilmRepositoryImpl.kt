package com.example.repository


import com.example.service_film.FilmService
import com.example.service_user.TokenParams
import com.example.service_user.UserService
import com.example.utils.Response

class FilmRepositoryImpl (private val filmService: FilmService) : FilmRepository {
    override suspend fun getNewFilm(params: TokenParams): Response<Any> {
        val user = filmService.findByToken(params.token)
        return if(user!=null){
            val film=filmService.findNewFilm()
            return Response.SuccessResponse(data=film)
        }
        else{
            Response.ErrorResponse(message="User does not exist")
        }
    }
}