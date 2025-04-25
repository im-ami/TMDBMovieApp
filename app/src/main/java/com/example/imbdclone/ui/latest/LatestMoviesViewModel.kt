package com.example.imbdclone.ui.latest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.usecase.FavoritesUseCase
import kotlinx.coroutines.launch

class LatestMoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<LatestMoviesUiState>()
    val uiState: LiveData<LatestMoviesUiState> = _uiState

    private var currentPage = 1
    private var isLoading = false
    private val favoriteMovieIds = mutableListOf<Int>()
    private val moviesList = mutableListOf<MovieData>()


    init {
        viewModelScope.launch {
            favoritesUseCase.loadFavoriteMovies().observeForever { favorites ->
                favoriteMovieIds.clear()
                favorites.forEach { favorite ->
                    favoriteMovieIds.add(favorite.movie_id)
                }
                updateMovieFavorites()
            }
        }
        loadNextPage()
    }

    private fun updateMovieFavorites() {
        val updatedMovies = moviesList.map { movie ->
            if (favoriteMovieIds.contains(movie.id)) {
                movie.copy(is_favorite = true)
            } else {
                movie.copy(is_favorite = false)
            }
        }
        _uiState.value = LatestMoviesUiState.Success(updatedMovies)
    }

    fun loadNextPage() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                val result = moviesRepository.fetchLatestMovies(currentPage)
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
                favoritesUseCase.addToFavorites(details)
            } catch (e: Exception) {
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    fun removeFromFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                favoritesUseCase.removeFromFavorites(details)
            } catch (e: Exception) {
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    sealed interface LatestMoviesUiState {
        data object Loading : LatestMoviesUiState
        data class Success(
            val latestMoviesList: List<MovieData>
        ) : LatestMoviesUiState
        data class Error(val message: String? = null) : LatestMoviesUiState
    }
}