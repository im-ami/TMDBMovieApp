package com.example.imbdclone.data.repository

import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.network.TMDBApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MoviesRepository {

    object RetrofitInstance {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        val api: TMDBApiService by lazy {
            retrofit.create(TMDBApiService::class.java)
        }
    }

    suspend fun fetchLatestMovies(nextPage: Int): List<MovieData> {
        val response = RetrofitInstance.api.getLatestMovies(
            page = nextPage
        )
        return response.results
    }

    suspend fun fetchMovieDetails(movieID: Int): MovieDetails {
        val response = RetrofitInstance.api.getMovieDetails(
            movieID = movieID
        )
        return response
    }

    suspend fun getMovieCredits(movieID: Int): List<Cast> {
        val response = RetrofitInstance.api.getMovieCredits(
            movieID = movieID
        )
        return response.cast
    }

    suspend fun getMovieImages(movieID: Int): List<MoviePosters> {
        val response = RetrofitInstance.api.getMovieImages(
            movieID = movieID
        )
        return response.backdrops
    }
}