package com.example.imbdclone.ui.latest

import com.example.imbdclone.data.model.MovieData

sealed interface LatestMoviesEvent {
    data class LaunchDetailsPage(val movie: MovieData): LatestMoviesEvent
    data class ToggleFavoriteButton(val movie: MovieData): LatestMoviesEvent
    data object LoadNextResultSet: LatestMoviesEvent
}