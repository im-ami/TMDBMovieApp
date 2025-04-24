package com.example.imbdclone.usecase

import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.repository.LocalRepository

class FavoritesUseCase(private val localRepository: LocalRepository) {

    suspend fun loadFavoriteMovies(): LiveData<List<FavoriteMovies>> {
        val favorites = localRepository.getFavorites()
        return favorites
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        return localRepository.isMovieFavorite(movieId)
    }

    suspend fun addToFavorites(details: FavoriteMovies) {
        val favorite = FavoriteMovies(
            movie_id = details.movie_id,
            is_favorite = true,
            title = details.title,
            backdrop_path = details.backdrop_path,
            vote_average = details.vote_average
        )
        localRepository.addToFavorites(favorite)
    }

    suspend fun removeFromFavorites(details: FavoriteMovies) {
        val favorite = FavoriteMovies(
            movie_id = details.movie_id,
            is_favorite = false,
            title = details.title,
            backdrop_path = details.backdrop_path,
            vote_average = details.vote_average
        )
        localRepository.removeFromFavorites(favorite)
    }
}