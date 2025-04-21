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

    private val _uiState = MutableLiveData<LatestMoviesUiState>()
    val uiState: LiveData<LatestMoviesUiState> = _uiState

    private var currentPage = 1
    private var isLoading = false
    private val moviesList = mutableListOf<MovieData>()

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading) return
        isLoading = true


        viewModelScope.launch {
            try {
                val result = moviesRepository.fetchLatestMovies(currentPage)
                moviesList.addAll(result)
                currentPage++

                _uiState.value = LatestMoviesUiState.Success(
                    result = moviesList.toList()
                )
                isLoading = false

            } catch (e: Exception) {
                isLoading = false
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    sealed class LatestMoviesUiState {
        data object Loading : LatestMoviesUiState()
        data class Success(
            val result: List<MovieData>
        ) : LatestMoviesUiState()
        data class Error(val message: String? = null) : LatestMoviesUiState()
    }
}