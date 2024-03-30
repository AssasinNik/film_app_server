package com.example.films

data class FilmDTO (
    val movie_id: Int,
    val title: String?,
    val titleEn: String?,
    val genres: List<String>,
    val posterURL: String,
    val rating: String,
    val length: Int,
    val description: String?,
    val releaseDate: String,
    val ageLimit: String?
)