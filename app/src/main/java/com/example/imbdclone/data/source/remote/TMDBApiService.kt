package com.example.imbdclone.data.source.remote

import com.example.imbdclone.BuildConfig
import com.example.imbdclone.data.model.CreditsData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MovieImages
import com.example.imbdclone.data.model.MovieResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TMDBApiService {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()

            return retrofit.create(TMDBApiService::class.java)
        }
    }
}