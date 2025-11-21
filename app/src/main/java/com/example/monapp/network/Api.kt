package com.example.monapp.network

import com.example.monapp.models.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): BooksResponse
}