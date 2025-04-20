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

    private val _movieData = MutableLiveData<MovieDetails>()
    val movieData: LiveData<MovieDetails> = _movieData

    private val _movieCredits = MutableLiveData<List<Cast>>()
    val movieCredits: LiveData<List<Cast>> = _movieCredits

    private val _movieImages = MutableLiveData<List<MoviePosters>>()
    val movieImages: LiveData<List<MoviePosters>> = _movieImages

    fun getMovieData(movieID: Int) {
        viewModelScope.launch {
            val movie = moviesRepository.fetchMovieDetails(movieID)
            _movieData.postValue(movie)
        }
    }

    fun getMovieCredits(movieID: Int) {
        viewModelScope.launch {
            val credits = moviesRepository.getMovieCredits(movieID)
            _movieCredits.postValue(credits)
        }
    }

    fun getMovieImages(movieID: Int) {
        viewModelScope.launch {
            val images = moviesRepository.getMovieImages(movieID)
            _movieImages.postValue(images)
        }
    }

}