package com.example.imbdclone.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.repository.MoviesRepository
import com.example.imbdclone.usecase.FavoritesUseCase
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<MovieDetailsUiState>()
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean> = _favoriteState

    fun loadMovieDetails(movieId: Int) {
        _uiState.value = MovieDetailsUiState.Loading

        viewModelScope.launch {
            try {
                val movie = moviesRepository.fetchMovieDetails(movieId)
                val credits = moviesRepository.getMovieCredits(movieId)
                val images = moviesRepository.getMovieImages(movieId)

                _uiState.value = MovieDetailsUiState.Success(
                    movie = movie,
                    credits = credits,
                    movieImages = images
                )
            } catch (e: Exception) {
                _uiState.value = MovieDetailsUiState.Error(e.message)
            }
        }
    }

    fun checkIfFavorites(movieId: Int) {
        viewModelScope.launch {
            _favoriteState.value = favoritesUseCase.isMovieFavorite(movieId = movieId)
        }
    }


    fun addToFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                favoritesUseCase.addToFavorites(details)
            } catch (e: Exception) {
                _uiState.value = MovieDetailsUiState.Error(e.message)
            }
        }
    }

    fun removeFromFavorites(details: FavoriteMovies) {
        viewModelScope.launch {
            try {
                favoritesUseCase.removeFromFavorites(details)
            } catch (e: Exception) {
                _uiState.value = MovieDetailsUiState.Error(e.message)
            }
        }
    }

    sealed interface MovieDetailsUiState {
        data object Loading : MovieDetailsUiState
        data class Success(
            val movie: MovieDetails,
            val credits: List<Cast>,
            val movieImages: List<MoviePosters>
        ) : MovieDetailsUiState
        data class Error(val message: String? = null) : MovieDetailsUiState
    }

}