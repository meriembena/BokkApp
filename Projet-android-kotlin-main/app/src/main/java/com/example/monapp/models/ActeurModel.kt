package com.example.monapp.models

data class ResultActors(
    val page: Int,
    val results: List<ActeurModel>,
    val total_pages: Int,
    val total_results: Int
)

data class ActeurModel(
    val adult: Boolean = false,
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String?,
    val media_type: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String?,
    val knownFor: List<KnownFor>? = null
)

data class KnownFor(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>?,
    val id: Int,
    val media_type: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)