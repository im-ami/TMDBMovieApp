package com.example.imbdclone.movie_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imbdclone.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _backdropPath = MutableLiveData<String>()
    val backdropPath: LiveData<String> = _backdropPath

    fun getBackdropPath(movieID: Int) {
        viewModelScope.launch {
            val movie = moviesRepository.fetchMovieDetails(movieID)
            _backdropPath.postValue(movie.backdrop_path)
        }
    }
}