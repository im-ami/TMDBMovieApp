package com.example.imbdclone.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.MovieData
import com.example.imbdclone.data.MoviesRepository
import kotlinx.coroutines.launch

class LatestMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<MovieData>>()
    val items: LiveData<List<MovieData>> = _items

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val movies = moviesRepository.fetchLatestMovies()
                Log.d("LatestMoviesVM", "Fetched ${movies.size} movies")
                _items.value = movies
            } catch (e: Exception) {
                Log.e("LatestMoviesVM", "API error: ${e.message}")
            }
        }
    }
}