package com.example.imbdclone.ui.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.usecase.FavoritesUseCase
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<FavoriteMoviesUiState>()
    val uiState: LiveData<FavoriteMoviesUiState> = _uiState

    private var isLoading = false
    private val favoritesList = mutableListOf<FavoriteMovies>()

    init {
        viewModelScope.launch {
            favoritesUseCase.loadFavoriteMovies().observeForever { favorites ->
                favoritesList.clear()
                favoritesList.addAll(favorites)
                submitFavoriteMovies()
            }
        }
    }

    private fun submitFavoriteMovies() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                _uiState.value = FavoriteMoviesUiState.Success(
                    favoriteMoviesList = favoritesList
                )
                isLoading = false

            } catch (e: Exception) {
                _uiState.value = FavoriteMoviesUiState.Error(e.message)
                isLoading = false
            }
        }
    }

    fun removeFromFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                favoritesUseCase.removeFromFavorites(details)
            } catch (e: Exception) {
                _uiState.value = FavoriteMoviesUiState.Error(e.message)
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
