package com.example.imbdclone.data.repository

import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.source.local.FavoriteMoviesDao

class LocalRepository(private val moviesDao: FavoriteMoviesDao) {

    fun getFavorites(): LiveData<List<FavoriteMovies>> = moviesDao.getFavorites()

    suspend fun isMovieFavorite(movieId: Int): Boolean = moviesDao.isMovieFavorite(movieId)

    suspend fun addToFavorites(movie: FavoriteMovies) = moviesDao.addToFavorites(movie)

    suspend fun removeFromFavorites(movie: FavoriteMovies) = moviesDao.removeFromFavorites(movie)
}
