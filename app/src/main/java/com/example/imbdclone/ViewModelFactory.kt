package com.example.imbdclone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imbdclone.data.MoviesRepository
import com.example.imbdclone.favorites.FavoriteMoviesViewModel
import com.example.imbdclone.popular.LatestMoviesViewModel

class MovieViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LatestMoviesViewModel::class.java)) {
            return LatestMoviesViewModel(moviesRepository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)) {
            return FavoriteMoviesViewModel(moviesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}