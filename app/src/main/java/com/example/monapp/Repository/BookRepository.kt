package com.example.monapp.repository

import com.example.monapp.data.local.dao.BookDao
import com.example.monapp.data.local.entity.toBook
import com.example.monapp.data.local.entity.toEntity
import com.example.monapp.models.Book
import com.example.monapp.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val apiService: ApiService,
    private val bookDao: BookDao
) {
    suspend fun searchBooks(query: String) = apiService.searchBooks(query)

    val allFavorites: Flow<List<Book>> = bookDao.getAllFavorites().map { entities ->
        entities.map { it.toBook() }
    }

    val favoritesCount: Flow<Int> = bookDao.getFavoritesCount()

    suspend fun addToFavorites(book: Book) {
        bookDao.insertFavorite(book.toEntity())
    }

    suspend fun removeFromFavorites(book: Book) {
        bookDao.deleteFavoriteByTitle(book.title)
    }

    suspend fun deleteAllFavorites() {
        bookDao.deleteAllFavorites()
    }
}