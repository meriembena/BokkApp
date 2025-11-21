package com.example.monapp

import com.example.monapp.models.ActeurModel
import com.example.monapp.models.CollectionResponse
import com.example.monapp.models.MovieDetails
import com.example.monapp.models.MovieResponse
import com.example.monapp.models.ResultActors
import com.example.monapp.models.ResultsSeries
import com.example.monapp.models.SerieDetails
import com.example.monapp.models.SeriesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //movies
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String): MovieResponse
    //acteurs
    @GET("trending/person/week")
    suspend fun lastActors(@Query("api_key") apikey: String):ResultActors
    //series
    @GET("trending/tv/week")
    suspend fun lastSeries(@Query("api_key")apikey: String) : ResultsSeries
    @GET ("tv/{id}")
    suspend fun serieInfo(@Path("id") id: String, @Query("api_key") apikey: String, @Query("append_to_response") append_to_response: String): SerieDetails
    //détails film
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetails
    @GET("person/{person_id}")
    suspend fun getActorDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): ActeurModel



    @GET("search/collection")
    suspend fun searchCollection(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): CollectionResponse

}