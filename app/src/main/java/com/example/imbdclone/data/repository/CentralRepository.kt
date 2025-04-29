package com.example.imbdclone.data.repository

import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters

interface CentralRepository {
    fun getFavorites(): LiveData<List<FavoriteMovies>>
    suspend fun isMovieFavorite(movieId: Int): Boolean
    suspend fun addToFavorites(movie: FavoriteMovies)
    suspend fun removeFromFavorites(movie: FavoriteMovies)

    suspend fun fetchLatestMovies(nextPage: Int): List<MovieData>
    suspend fun fetchMovieDetails(movieID: Int): MovieDetails
    suspend fun getMovieCredits(movieID: Int): List<Cast>
    suspend fun getMovieImages(movieID: Int): List<MoviePosters>
}
