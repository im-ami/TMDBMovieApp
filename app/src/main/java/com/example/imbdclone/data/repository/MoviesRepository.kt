package com.example.imbdclone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.source.local.FavoriteMoviesDao
import com.example.imbdclone.data.source.remote.TMDBApiService

class MoviesRepository(
    private val api: TMDBApiService,
    private val moviesDao: FavoriteMoviesDao
) {

    suspend fun getFavorites(): List<FavoriteMovies> {
        val response = moviesDao.getFavorites()
        Log.d("FavoritesList", "Favorites now: $response")
        return response
    }

    fun isMovieFavorite(movie_id: Int): LiveData<Boolean> {
        val response = moviesDao.isMovieFavorite(movie_id)
        return response
    }

    suspend fun addToFavorites(movie: FavoriteMovies) {
        Log.d("FavoritesRepo", "Adding movie: ${movie.movie_id}")
        moviesDao.addToFavorites(movie)
    }

    suspend fun removeFromFavorites(movie: FavoriteMovies) {
        Log.d("FavoritesRepo", "Removing movie: ${movie.movie_id}")
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