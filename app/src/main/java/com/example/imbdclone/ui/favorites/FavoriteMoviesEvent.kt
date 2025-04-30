package com.example.imbdclone.ui.favorites

import com.example.imbdclone.data.model.FavoriteMovies

sealed interface FavoriteMoviesEvent {
    data class RemoveFromFavorites(val movie: FavoriteMovies) : FavoriteMoviesEvent
}