package com.example.imbdclone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imbdclone.data.repository.CentralRepositoryImpl
import com.example.imbdclone.ui.favorites.FavoriteMoviesViewModel
import com.example.imbdclone.ui.latest.LatestMoviesViewModel
import com.example.imbdclone.ui.movie_details.MovieDetailsViewModel

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val repository: CentralRepositoryImpl) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LatestMoviesViewModel::class.java)) {
            return LatestMoviesViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)) {
            return FavoriteMoviesViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}