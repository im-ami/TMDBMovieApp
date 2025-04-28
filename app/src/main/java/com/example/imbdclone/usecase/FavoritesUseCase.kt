package com.example.imbdclone.usecase

import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.repository.CentralRepository

class FavoritesUseCase(private val repository: CentralRepository) {

    fun loadFavoriteMovies(): LiveData<List<FavoriteMovies>> {
        val favorites = repository.getFavorites()
        return favorites
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        return repository.isMovieFavorite(movieId)
    }

    suspend fun addToFavorites(details: FavoriteMovies) {
        val favorite = FavoriteMovies(details.movie_id, true, details.title, details.backdrop_path, details.vote_average)
        repository.addToFavorites(favorite)
    }

    suspend fun removeFromFavorites(details: FavoriteMovies) {
        val favorite = FavoriteMovies(details.movie_id, false, details.title, details.backdrop_path, details.vote_average)
        repository.removeFromFavorites(favorite)
    }
}