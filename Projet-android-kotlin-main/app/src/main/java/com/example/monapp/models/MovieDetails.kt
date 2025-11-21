package com.example.monapp.models
import com.squareup.moshi.Json

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "poster_path") val posterPath: String?
)