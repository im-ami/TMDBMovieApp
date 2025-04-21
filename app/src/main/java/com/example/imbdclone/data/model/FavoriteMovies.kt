package com.example.imbdclone.data.model

data class FavoriteMoviesList(
    val page: Int,
    val results: List<FavoriteMovies>
)

data class FavoriteMovies(
    val id: Int,
    val original_title: String,
    val popularity: Double,
    val vote_average: Double
)