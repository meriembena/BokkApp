package com.example.monapp.repository

import com.example.monapp.network.ApiService

class BookRepository(private val apiService: ApiService) {

    suspend fun searchBooks(query: String) = apiService.searchBooks(query)
}
