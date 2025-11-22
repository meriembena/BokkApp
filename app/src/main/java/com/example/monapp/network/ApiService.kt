package com.example.monapp.network

import com.example.monapp.models.BooksResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService {

    private val client = HttpClientProvider.client

    suspend fun searchBooks(query: String): BooksResponse {
        return client.get("https://openlibrary.org/search.json") {
            parameter("q", query)
            accept(ContentType.Application.Json)
        }.body()

    }
}
