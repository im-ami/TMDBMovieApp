package com.example.imbdclone.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.ui.favorites.FavoriteMoviesEvent
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters
import com.example.imbdclone.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<MovieDetailsUiState>()
    val uiState: LiveData<MovieDetailsUiState> = _uiState

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

    fun isMovieFavorite(movieId: Int): LiveData<Boolean> {
        return moviesRepository.isMovieFavorite(movieId)
    }


    fun onEvent(event: FavoriteMoviesEvent) {
        val currentState = _uiState.value
        if (currentState is MovieDetailsUiState.Success) {
            val movie = currentState.movie

            when (event) {
                is FavoriteMoviesEvent.AddToFavorites -> {
                    viewModelScope.launch {
                        val favorite = FavoriteMovies(
                            movie_id = movie.id,
                            is_favorite = true,
                            title = movie.title,
                            backdrop_path = movie.backdrop_path,
                            vote_average = movie.vote_average,
                        )
                        moviesRepository.addToFavorites(favorite)
                    }
                }

                is FavoriteMoviesEvent.RemoveFromFavorites -> {
                    viewModelScope.launch {
                        val favorite = FavoriteMovies(
                            movie_id = movie.id,
                            is_favorite = true,
                            title = movie.title,
                            backdrop_path = movie.backdrop_path,
                            vote_average = movie.vote_average,
                        )
                        moviesRepository.removeFromFavorites(favorite)
                    }
                }
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