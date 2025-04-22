package com.example.imbdclone.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<FavoriteMoviesUiState>()
    val uiState: LiveData<FavoriteMoviesUiState> = _uiState

    private var isLoading = false
    private val favoritesList = mutableListOf<FavoriteMovies>()

    init {
        loadFavoriteMovies()
    }

    fun loadFavoriteMovies() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val movies = moviesRepository.getFavorites()
                favoritesList.addAll(movies)

                _uiState.value = FavoriteMoviesUiState.Success(
                    favoriteMoviesList = favoritesList.toList()
                )
                isLoading = false

            } catch (e: Exception) {
                _uiState.value = FavoriteMoviesUiState.Error(e.message)
                isLoading = false
            }
        }
    }

    sealed interface FavoriteMoviesUiState {
        data class Success(
            val favoriteMoviesList: List<FavoriteMovies>
        ) : FavoriteMoviesUiState
        data class Error(val message: String? = null) : FavoriteMoviesUiState
    }
}
