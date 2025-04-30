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

    private val _navigateToDetails = MutableLiveData<MovieData?>()
    val navigateToDetails: LiveData<MovieData?> = _navigateToDetails

    private var currentPage = 1
    private var isLoading = false
    private val favoriteMovieIds = mutableSetOf<Int>()
    private val moviesList = mutableSetOf<MovieData>()

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

    fun handleEvent(event: LatestMoviesEvent) {
        when (event) {
            is LatestMoviesEvent.ToggleFavoriteButton -> {
                val favoriteMovie = FavoriteMovies(
                    event.movie.id, event.movie.isFavorite,
                    event.movie.title, event.movie.posterPath, event.movie.voteAverage
                )
                if (event.movie.isFavorite) {
                    removeFromFavorites(favoriteMovie)
                } else {
                    addToFavorites(favoriteMovie)
                }
            }
            is LatestMoviesEvent.LoadNextResultSet -> {
                loadNextPage()
            }

            is LatestMoviesEvent.LaunchDetailsPage -> {
                _navigateToDetails.value = event.movie
            }
        }
    }

    fun clearNavigationEvent() {
        _navigateToDetails.value = null
    }

    private fun updateMovieFavorites() {
        val updatedMovies = moviesList.map { movie ->
            if (favoriteMovieIds.contains(movie.id)) {
                movie.copy(isFavorite = true)
            } else {
                movie.copy(isFavorite = false)
            }
        }
        _uiState.value = LatestMoviesUiState.Success(updatedMovies.toList())
    }

    private fun loadNextPage() {
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

    private fun addToFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                repository.addToFavorites(details)
            } catch (e: Exception) {
                _uiState.value = LatestMoviesUiState.Error(e.message)
            }
        }
    }

    private fun removeFromFavorites(details: FavoriteMovies) {
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
}