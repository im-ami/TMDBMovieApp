package com.example.imbdclone.ui.favorites

import com.example.imbdclone.data.model.FavoriteMovies

sealed interface FavoriteMoviesUiState {
    data object Loading : FavoriteMoviesUiState
    data class Success(
        val favoriteMoviesList: List<FavoriteMovies>
    ) : FavoriteMoviesUiState
    data class Error(val message: String? = null) : FavoriteMoviesUiState
}