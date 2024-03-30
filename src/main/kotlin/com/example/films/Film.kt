package com.example.films

import org.jetbrains.exposed.sql.Table

object Film: Table("movies") {
    val filmid = Film.integer("filmid").autoIncrement()
    val title=Film.text("name")
    val titleEN = Film.text("alternativename")
    val releaseDate = Film.integer("release_year")
    val ageLimit = Film.integer("agerating")
    val genres = Film.text("genres")
    val poster_url=Film.text("poster_url")
    val rating_kp=Film.float("rating_kp")
    val movie_length=Film.integer("movie_length")
    val description=Film.text("description")

    override val primaryKey=PrimaryKey(filmid)
}