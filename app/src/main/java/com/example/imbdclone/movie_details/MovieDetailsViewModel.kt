package com.example.imbdclone.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movieData = MutableLiveData<MovieDetails>()
    val movieData: LiveData<MovieDetails> = _movieData

    fun getMovieData(movieID: Int) {
        viewModelScope.launch {
            val movie = moviesRepository.fetchMovieDetails(movieID)
            _movieData.postValue(movie)
        }
    }

    //launch a parallel API call for characters
}