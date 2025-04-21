package com.example.imbdclone.data.repository

import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
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
        return try {
            val response = RetrofitInstance.api.getLatestMovies(
                page = nextPage)
            response.results

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchMovieDetails(movieID: Int): MovieDetails {
        return try {
            val response = RetrofitInstance.api.getMovieDetails(
                movieID = movieID
            )
            response
        } catch (e: Exception) {
            throw e
        }

    }

    suspend fun getMovieCredits(movieID: Int): List<Cast> {
        return try {
            val response = RetrofitInstance.api.getMovieCredits(
                movieID = movieID
            )
            response.cast
        } catch (e: Exception) {
            throw e
        }

    }

    suspend fun getMovieImages(movieID: Int): List<MoviePosters> {
        return try {
            val response = RetrofitInstance.api.getMovieImages(
                movieID = movieID
            )
            response.backdrops
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addToFavorites(accountID: Int) {
        return try {
            RetrofitInstance.api.addToFavorites(accountID = accountID)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getFavorites(accountID: Int, page: Int): List<FavoriteMovies> {
        return try {
            val response = RetrofitInstance.api.getFavorites(
                accountID = accountID,
                page = page)
            response.results
        } catch (e: Exception) {
            throw e
        }
    }
}