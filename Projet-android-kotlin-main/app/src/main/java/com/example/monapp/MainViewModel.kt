package com.example.monapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monapp.models.ActeurModel
import com.example.monapp.models.Movie
import com.example.monapp.models.MovieDetails
import com.example.monapp.models.MovieHorror
import com.example.monapp.models.SerieDetails
import com.example.monapp.models.SeriesModel
import com.example.monapp.models.genreList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val api = retrofit.create(Api::class.java)
    private val api_key = "b57151d36fecd1b693da830a2bc5766f"
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    fun getImageUrl(path: String?, size: String = "w500"): String {
        return "https://image.tmdb.org/t/p/$size$path"
    }

    // Films
    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: StateFlow<List<Movie>> = searchQuery
        .combine(_movies) { query, movies ->
            if (query.isBlank()) movies else movies.filter { it.title.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getTrendingMovies() {
        viewModelScope.launch {
            try {
                val response = api.lastmovies(api_key)
                _movies.value = response.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors du chargement des films : ${e.message}")
            }
        }
    }

    // Acteurs
    private val _acteurs = MutableStateFlow<List<ActeurModel>>(listOf())
    val acteurs: StateFlow<List<ActeurModel>> = searchQuery
        .combine(_acteurs) { query, acteurs ->
            if (query.isBlank()) acteurs else acteurs.filter { it.name.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun lastActors() {
        viewModelScope.launch {
            try {
                val response = api.lastActors(api_key)
                _acteurs.value = response.results

                Log.d("MainViewModel", "Acteurs chargés: ${response.results.size}")

            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors du chargement des acteurs : ${e.message}")
            }
        }

    }


    // Séries
    private val _series = MutableStateFlow<List<SeriesModel>>(listOf())
    val series: StateFlow<List<SeriesModel>> = searchQuery
        .combine(_series) { query, series ->
            if (query.isBlank()) series else series.filter { it.name.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun lastSeries() {
        viewModelScope.launch {
            _series.value = api.lastSeries(api_key).results
        }
    }

    //recherhce
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Détails film
    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val details = api.getMovieDetails(movieId, api_key)
                _movieDetails.value = details
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors du chargement des détails du film : ${e.message}")
            }
        }
    }

    // série détails
    private val _serieDetails = MutableStateFlow<SerieDetails?>(null)
    val serieDetails: StateFlow<SerieDetails?> = _serieDetails

    fun getSeriesDetails(seriesId: Int) {
        viewModelScope.launch {
            try {
                val details = api.serieInfo(seriesId.toString(), api_key, "videos,credits")
                _serieDetails.value = details
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors du chargement des détails de la série : ${e.message}")
            }
        }
    }

    fun getGenreNames(genreIds: List<Int>): String {
        return genreIds.mapNotNull { id ->
            genreList.find { it.id == id }?.name
        }.joinToString(", ")
    }
    // acteur détails
    private val _actorDetails = MutableStateFlow<ActeurModel?>(null)
    val actorDetails: StateFlow<ActeurModel?> = _actorDetails.asStateFlow()

    fun getActorDetails(actorId: Int) {
        viewModelScope.launch {
            try {
                val actor = api.getActorDetails(actorId, api_key)
                _actorDetails.value = actor
            } catch (e: Exception) {
                Log.e("ActorDetails", "Erreur de récupération des détails de l'acteur", e)
                _actorDetails.value = null
            }
        }
    }

    //Collections (Horror)
    private val _horrorCollections = MutableStateFlow<List<MovieHorror>>(listOf())
    val horrorCollections: StateFlow<List<MovieHorror>> = _horrorCollections

    fun searchHorrorCollections() {
        viewModelScope.launch {
            try {
                val response = api.searchCollection(api_key, "horror")
                _horrorCollections.value = response.results
                Log.d("MainViewModel", "Collections trouvées: ${response.results.size}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Erreur lors du chargement des collections : ${e.message}")
            }
        }
    }

}


