package com.example.imbdclone.data.repository

import android.util.Log
import com.example.imbdclone.data.model.MovieData
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

    suspend fun fetchMovieDetails(movieID: Int): MovieData {
        val response = RetrofitInstance.api.getMovieDetails(
            movieID = movieID
        )
        return response
    }
}