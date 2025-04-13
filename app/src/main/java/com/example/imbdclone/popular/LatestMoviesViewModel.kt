package com.example.imbdclone.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class LatestMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _items = MutableLiveData<List<MovieData>>()
    val items: LiveData<List<MovieData>> = _items

    private var currentPage = 1
    private var isLoading = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            val result = moviesRepository.fetchLatestMovies(currentPage)
            currentPage++

            val currentList = _items.value ?: emptyList()
            val updatedList = currentList + result
            _items.value = updatedList

            isLoading = false
        }
    }
}