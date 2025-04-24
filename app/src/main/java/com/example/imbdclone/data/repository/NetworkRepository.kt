package com.example.imbdclone.data.repository

import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.source.remote.TMDBApiService

class MoviesRepository(private val api: TMDBApiService) {

    suspend fun fetchLatestMovies(nextPage: Int): List<MovieData> {
        return try {
            val response = api.getLatestMovies(
                page = nextPage)
            response.results

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchMovieDetails(movieID: Int): MovieDetails {
        return try {
            val response = api.getMovieDetails(
                movieID = movieID
            )
            response
        } catch (e: Exception) {
            throw e
        }

    }

    suspend fun getMovieCredits(movieID: Int): List<Cast> {
        return try {
            val response = api.getMovieCredits(
                movieID = movieID
            )
            response.cast
        } catch (e: Exception) {
            throw e
        }

    }

    suspend fun getMovieImages(movieID: Int): List<MoviePosters> {
        return try {
            val response = api.getMovieImages(
                movieID = movieID
            )
            response.backdrops
        } catch (e: Exception) {
            throw e
        }
    }
}