package com.example.imbdclone.ui.latest

import com.example.imbdclone.data.model.MovieData

sealed interface LatestMoviesUiState {
    data object Loading : LatestMoviesUiState
    data class Success(
        val result: List<MovieData>
    ) : LatestMoviesUiState
    data class Error(val message: String? = null) : LatestMoviesUiState
}