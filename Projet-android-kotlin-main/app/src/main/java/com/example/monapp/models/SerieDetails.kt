package com.example.monapp.models

data class SerieDetails(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val first_air_date: String,
    val genres: List<Genre>,
    val vote_average: Double,
    val vote_count: Int,
    val seasons: List<Season>
)

data class Genre(
    val id: Int,
    val name: String
)

data class Season(
    val season_number: Int,
    val name: String,
    val air_date: String,
    val episode_count: Int,
    val poster_path: String?
)
