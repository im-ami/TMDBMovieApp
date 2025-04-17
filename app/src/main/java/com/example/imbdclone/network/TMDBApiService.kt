package com.example.imbdclone.network

import com.example.imbdclone.BuildConfig
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    @GET("trending/movie/day")
    suspend fun getLatestMovies(
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieID: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieData
}