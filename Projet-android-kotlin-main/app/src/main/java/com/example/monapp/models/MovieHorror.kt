package com.example.monapp.models

data class CollectionResponse(
    val page: Int,
    val results: List<MovieHorror>
)

data class MovieHorror(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?
)
