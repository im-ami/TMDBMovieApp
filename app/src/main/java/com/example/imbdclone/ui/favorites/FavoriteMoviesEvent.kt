package com.example.imbdclone.ui.favorites

sealed interface FavoriteMoviesEvent {
    data class RemoveFromFavorites(val movieId: Int) : FavoriteMoviesEvent
}