package com.example.imbdclone.data.repository

import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.source.local.FavoriteMoviesDao
import com.example.imbdclone.data.source.remote.TMDBApiService

class CentralRepository(
    private val api: TMDBApiService,
    private val moviesDao: FavoriteMoviesDao) {
    
    fun getFavorites(): LiveData<List<FavoriteMovies>> {
        val response = moviesDao.getFavorites()
        return response
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        val response = moviesDao.isMovieFavorite(movieId)
        return response
    }

    suspend fun addToFavorites(movie: FavoriteMovies) {
        moviesDao.addToFavorites(movie)
    }

    suspend fun removeFromFavorites(movie: FavoriteMovies) {
        moviesDao.removeFromFavorites(movie)
    }

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