package com.example.monapp.models

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val poster_path: String
)