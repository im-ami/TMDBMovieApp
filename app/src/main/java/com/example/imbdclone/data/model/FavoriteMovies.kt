package com.example.imbdclone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovies(
    @PrimaryKey
    val movie_id: Int,
    val is_favorite: Boolean,
    val title: String,
    val backdrop_path: String?,
    val vote_average: Double
)