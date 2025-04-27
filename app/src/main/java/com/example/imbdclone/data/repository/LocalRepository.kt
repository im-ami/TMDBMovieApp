package com.example.imbdclone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.source.local.FavoriteMoviesDao

class LocalRepository(private val moviesDao: FavoriteMoviesDao) {

    suspend fun getFavorites(): LiveData<List<FavoriteMovies>> {
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
}