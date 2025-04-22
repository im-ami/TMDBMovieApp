package com.example.imbdclone.ui.favorites

sealed interface FavoriteMoviesEvent {
    data class AddToFavorites(val movieId: Int) : FavoriteMoviesEvent
    data class RemoveFromFavorites(val movieId: Int) : FavoriteMoviesEvent
}