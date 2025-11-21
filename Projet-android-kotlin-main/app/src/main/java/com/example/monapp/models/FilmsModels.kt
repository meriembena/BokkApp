package com.example.monapp.models

data class MovieResponse(
    val page: Int = 0,
    val results: List<Movie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class GenreFilm(val id: Int, val name: String)


val genreList = listOf(
    Genre(28, "Action"),
    Genre(12, "Aventure"),
    Genre(16, "Animation"),
    Genre(35, "Comédie"),
    Genre(80, "Crime"),
    Genre(99, "Documentaire"),
    Genre(18, "Drame"),
    Genre(10751, "Famille"),
    Genre(14, "Fantastique"),
    Genre(36, "Histoire"),
    Genre(27, "Horreur"),
    Genre(10402, "Musique"),
    Genre(9648, "Mystère"),
    Genre(10749, "Romance"),
    Genre(878, "Science-fiction"),
    Genre(10770, "Téléfilm"),
    Genre(53, "Thriller"),
    Genre(10752, "Guerre"),
    Genre(37, "Western")
)


