package com.example.monapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.monapp.data.local.database.AppDatabase
import com.example.monapp.models.Book
import com.example.monapp.repository.BookRepository
import com.example.monapp.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository

    init {
        val database = AppDatabase.getDatabase(application)
        val bookDao = database.bookDao()
        repository = BookRepository(ApiService(), bookDao)
    }

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun searchBooks(query: String = "science") {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.searchBooks(query)
                _books.value = response.docs
            } catch (e: Exception) {
                _error.value = "Erreur: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    val favorites: StateFlow<List<Book>> = run {
        val mutableFlow = MutableStateFlow<List<Book>>(emptyList())
        viewModelScope.launch {
            repository.allFavorites.collect { favoritesList ->
                mutableFlow.value = favoritesList
            }
        }
        mutableFlow.asStateFlow()
    }

    val favoritesCount: StateFlow<Int> = run {
        val mutableFlow = MutableStateFlow(0)
        viewModelScope.launch {
            repository.favoritesCount.collect { count ->
                mutableFlow.value = count
            }
        }
        mutableFlow.asStateFlow()
    }

    fun addToFavorites(book: Book) {
        viewModelScope.launch {
            try {
                repository.addToFavorites(book)
            } catch (e: Exception) {
                _error.value = "Erreur ajout favoris"
                e.printStackTrace()
            }
        }
    }

    fun removeFromFavorites(book: Book) {
        viewModelScope.launch {
            try {
                repository.removeFromFavorites(book)
            } catch (e: Exception) {
                _error.value = "Erreur suppression favoris"
                e.printStackTrace()
            }
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            try {
                repository.deleteAllFavorites()
            } catch (e: Exception) {
                _error.value = "Erreur suppression"
                e.printStackTrace()
            }
        }
    }
}