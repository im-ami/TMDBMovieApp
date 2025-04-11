package com.example.imbdclone.network

import com.example.imbdclone.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("trending/movie/day")
    suspend fun getLatestMovies(
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): MovieResponse
}