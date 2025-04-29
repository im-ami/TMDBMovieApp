package com.example.imbdclone.data.repository

import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.source.remote.TMDBApiService

class RemoteRepository(private val api: TMDBApiService) {

    suspend fun fetchLatestMovies(nextPage: Int): List<MovieData> {
        return try {
            api.getLatestMovies(page = nextPage).results
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun fetchMovieDetails(movieID: Int): MovieDetails {
        return try {
            api.getMovieDetails(movieID)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getMovieCredits(movieID: Int): List<Cast> {
        return try {
            api.getMovieCredits(movieID).cast
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getMovieImages(movieID: Int): List<MoviePosters> {
        return try {
            api.getMovieImages(movieID).backdrops
        } catch (e: Exception) {
            throw e
        }
    }
}
