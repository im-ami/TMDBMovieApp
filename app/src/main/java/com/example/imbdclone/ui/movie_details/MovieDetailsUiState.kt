package com.example.imbdclone.ui.movie_details

import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState
    data class Success(
        val movie: MovieDetails,
        val credits: List<Cast>,
        val movieImages: List<MoviePosters>
    ) : MovieDetailsUiState
    data class Error(val message: String? = null) : MovieDetailsUiState
}