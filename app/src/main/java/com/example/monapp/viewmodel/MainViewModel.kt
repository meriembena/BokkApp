package com.example.monapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monapp.models.Book
import com.example.monapp.repository.BookRepository
import com.example.monapp.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _favorites = MutableStateFlow<MutableSet<Book>>(mutableSetOf())
    val favorites: StateFlow<Set<Book>> = _favorites.asStateFlow()

    private val repository = BookRepository(ApiService())

    fun searchBooks(query: String = "") {
        viewModelScope.launch {
            try {
                val response = repository.searchBooks(query)
                _books.value = response.docs
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun addToFavorites(book: Book) {
        val newSet = _favorites.value.toMutableSet()
        newSet.add(book)
        _favorites.value = newSet
    }

    fun removeFromFavorites(book: Book) {
        val newSet = _favorites.value.toMutableSet()
        newSet.remove(book)
        _favorites.value = newSet
    }

    fun isFavorite(book: Book): Boolean {
        return _favorites.value.contains(book)
    }
}
