package com.example.monapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monapp.models.Book
import com.example.monapp.network.Api
import com.example.monapp.repository.BookRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _favorites = MutableStateFlow<MutableSet<Book>>(mutableSetOf())
    val favorites: StateFlow<Set<Book>> = _favorites.asStateFlow()

    private val repository: BookRepository

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://openlibrary.org/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val api = retrofit.create(Api::class.java)
        repository = BookRepository(api)
    }


    fun searchBooks(query: String = "harry potter") {
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
        _favorites.value.add(book)
        _favorites.value = _favorites.value.toMutableSet()

        fun removeFromFavorites(book: Book) {
            _favorites.value.remove(book)
            _favorites.value = _favorites.value.toMutableSet()
        }

        fun isFavorite(book: Book): Boolean {
            return _favorites.value.contains(book)
        }
    }}
