package com.example.imbdclone.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

    private val favoritesList = mutableListOf<FavoriteMovies>()
    private val observer = Observer<List<FavoriteMovies>> { favorites ->
        favoritesList.clear()
        favoritesList.addAll(favorites)
        _uiState.value = FavoriteMoviesUiState.Success(favoriteMoviesList = favoritesList)
    }

    init {
        viewModelScope.launch {
            favoritesUseCase.loadFavoriteMovies().observeForever(observer)
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

    override fun onCleared() {
        super.onCleared()
        favoritesUseCase.loadFavoriteMovies().removeObserver(observer)
    }

    sealed interface FavoriteMoviesUiState {
        data class Success(
            val favoriteMoviesList: List<FavoriteMovies>
        ) : FavoriteMoviesUiState
        data class Error(val message: String? = null) : FavoriteMoviesUiState
    }
}
