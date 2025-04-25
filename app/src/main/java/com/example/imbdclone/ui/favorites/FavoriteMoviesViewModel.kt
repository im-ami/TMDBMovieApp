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

    init {
        viewModelScope.launch {
            favoritesUseCase.loadFavoriteMovies().observeForever { favorites ->
                submitFavoriteMovies(favorites)
            }
        }
    }

    private fun submitFavoriteMovies(favorites: List<FavoriteMovies>) {
        _uiState.value = FavoriteMoviesUiState.Success(favorites)
    }

    fun removeFromFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                favoritesUseCase.removeFromFavorites(details)
                Log.d("FAV VIEW MODEL", "remove from favs is being called")
            } catch (e: Exception) {
                _uiState.value = FavoriteMoviesUiState.Error(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(this.javaClass.name, "Cleared Fav Viewmodel")
    }

    sealed interface FavoriteMoviesUiState {
        data object Loading : FavoriteMoviesUiState
        data class Success(
            val favoriteMoviesList: List<FavoriteMovies>
        ) : FavoriteMoviesUiState

        data class Error(val message: String? = null) : FavoriteMoviesUiState
    }
}
