package com.example.imbdclone.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.MovieData
import com.example.imbdclone.data.MoviesRepository
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<MovieData>>()
    val items: LiveData<List<MovieData>> = _items

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
        }
    }
}
