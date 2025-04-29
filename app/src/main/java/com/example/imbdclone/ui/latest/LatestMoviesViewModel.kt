package com.example.imbdclone.ui.latest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.repository.CentralRepositoryImpl
import kotlinx.coroutines.launch

class LatestMoviesViewModel(
    private val repository: CentralRepositoryImpl) : ViewModel() {

    private val _uiState = MutableLiveData<LatestMoviesUiState>()
    val uiState: LiveData<LatestMoviesUiState> = _uiState

    private var currentPage = 1
    private var isLoading = false
    private val favoriteMovieIds = mutableListOf<Int>()
    private val moviesList = mutableListOf<MovieData>()

    private val favoritesObserver = Observer<List<FavoriteMovies>> { favorites ->
        favoriteMovieIds.clear()
        favorites.forEach { favorite ->
            favoriteMovieIds.add(favorite.movieId)
        }
        updateMovieFavorites()
    }

    init {
        viewModelScope.launch {
            repository.getFavorites().observeForever(favoritesObserver)
        }
        loadNextPage()
    }

    private fun updateMovieFavorites() {
        val updatedMovies = moviesList.map { movie ->
            if (favoriteMovieIds.contains(movie.id)) {
                movie.copy(isFavorite = true)
            } else {
                movie.copy(isFavorite = false)
            }
        }
        _uiState.value = LatestMoviesUiState.Success(updatedMovies)
    }

    fun loadNextPage() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val result = repository.fetchLatestMovies(currentPage)
                moviesList.addAll(result)
                updateMovieFavorites()
                currentPage++
                isLoading = false

            } catch (e: Exception) {
                isLoading = false
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    fun addToFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                repository.addToFavorites(details)
            } catch (e: Exception) {
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    fun removeFromFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                repository.removeFromFavorites(details)
            } catch (e: Exception) {
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.getFavorites().removeObserver(favoritesObserver)
    }

    sealed interface LatestMoviesUiState {
        data object Loading : LatestMoviesUiState
        data class Success(
            val result: List<MovieData>
        ) : LatestMoviesUiState
        data class Error(val message: String? = null) : LatestMoviesUiState
    }
}