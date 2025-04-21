package com.example.imbdclone.network

import com.example.imbdclone.BuildConfig
import com.example.imbdclone.data.model.CreditsData
import com.example.imbdclone.data.model.FavoriteMoviesList
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MovieImages
import com.example.imbdclone.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.POST
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
    ): MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): CreditsData

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieImages

    @POST("account/{account_id}/favorite")
    suspend fun addToFavorites(
        @Path("account_id") accountID: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    )

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Path("account_id") accountID: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int
    ): FavoriteMoviesList
}