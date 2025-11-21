package com.example.monapp.repository

import com.example.monapp.network.Api

class BookRepository(private val api: Api) {

    suspend fun searchBooks(query: String) = api.searchBooks(query)
}
