package com.example.imbdclone.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val accountID = 21934834

    private val _uiState = MutableLiveData<MovieDetailsUiState>()
    val uiState: LiveData<MovieDetailsUiState> = _uiState

    fun addToFavorites() {
        viewModelScope.launch {
            moviesRepository.addToFavorites(accountID)
        }
    }

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

    sealed class MovieDetailsUiState {
        data object Loading : MovieDetailsUiState()
        data class Success(
            val movie: MovieDetails,
            val credits: List<Cast>,
            val movieImages: List<MoviePosters>
        ) : MovieDetailsUiState()
        data class Error(val message: String? = null) : MovieDetailsUiState()
    }

}