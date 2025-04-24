package com.example.imbdclone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.ui.favorites.FavoriteMoviesViewModel
import com.example.imbdclone.ui.movie_details.MovieDetailsViewModel
import com.example.imbdclone.ui.latest.LatestMoviesViewModel
import com.example.imbdclone.usecase.FavoritesUseCase

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val favoritesUseCase: FavoritesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LatestMoviesViewModel::class.java)) {
            return LatestMoviesViewModel(moviesRepository, favoritesUseCase) as T
        }
        if (modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)) {
            return FavoriteMoviesViewModel(favoritesUseCase) as T
        }
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(moviesRepository, favoritesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}