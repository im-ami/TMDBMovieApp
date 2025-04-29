package com.example.imbdclone.data.repository

import androidx.lifecycle.LiveData
import com.example.imbdclone.data.model.Cast
import com.example.imbdclone.data.model.FavoriteMovies
import com.example.imbdclone.data.model.MovieData
import com.example.imbdclone.data.model.MovieDetails
import com.example.imbdclone.data.model.MoviePosters

class CentralRepositoryImpl(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : CentralRepository {

    override fun getFavorites(): LiveData<List<FavoriteMovies>> = localRepository.getFavorites()

    override suspend fun isMovieFavorite(movieId: Int): Boolean = localRepository.isMovieFavorite(movieId)

    override suspend fun addToFavorites(movie: FavoriteMovies) = localRepository.addToFavorites(movie)

    override suspend fun removeFromFavorites(movie: FavoriteMovies) = localRepository.removeFromFavorites(movie)

    override suspend fun fetchLatestMovies(nextPage: Int): List<MovieData> = remoteRepository.fetchLatestMovies(nextPage)

    override suspend fun fetchMovieDetails(movieID: Int): MovieDetails = remoteRepository.fetchMovieDetails(movieID)

    override suspend fun getMovieCredits(movieID: Int): List<Cast> = remoteRepository.getMovieCredits(movieID)

    override suspend fun getMovieImages(movieID: Int): List<MoviePosters> = remoteRepository.getMovieImages(movieID)
}
