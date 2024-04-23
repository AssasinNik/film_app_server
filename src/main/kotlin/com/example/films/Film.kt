package com.example.films
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.TextColumnType

object Film: Table("movies") {
    val filmid: Column<Int> = Film.integer("filmid")
    val title: Column<String> =Film.text("name")
    val titleEN: Column<String> = Film.text("alternativename")
    val releaseDate: Column<Int> = Film.integer("release_year")
    val ageLimit: Column<Int> = Film.integer("agerating")
    val genres = Film.array<String>("genres")
    val poster_url: Column<String> =Film.text("poster_url")
    val rating_kp: Column<Float> =Film.float("rating_kp")
    val movie_length: Column<Int> =Film.integer("movie_length")
    val description: Column<String> =Film.text("description")
    override val primaryKey=PrimaryKey(filmid)
}


