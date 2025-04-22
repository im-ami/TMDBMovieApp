package com.example.imbdclone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.ui.favorites.FavoriteMoviesViewModel
import com.example.imbdclone.ui.movie_details.MovieDetailsViewModel
import com.example.imbdclone.ui.popular.LatestMoviesViewModel

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LatestMoviesViewModel::class.java)) {
            return LatestMoviesViewModel(moviesRepository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)) {
            return FavoriteMoviesViewModel(moviesRepository) as T
        }
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(moviesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}