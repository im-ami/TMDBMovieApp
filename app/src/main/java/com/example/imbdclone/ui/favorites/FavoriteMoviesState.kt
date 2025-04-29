package com.example.imbdclone.ui.favorites

import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.ui.latest.LatestMoviesViewModel.LatestMoviesUiState

sealed interface FavoriteMoviesUiState {
    data object Loading : FavoriteMoviesUiState
    data class Success(
        val favoriteMoviesList: List<FavoriteMovies>
    ) : FavoriteMoviesUiState
    data class Error(val message: String? = null) : FavoriteMoviesUiState
}