package com.example.imbdclone.data.model

import com.squareup.moshi.Json

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val original_title: String,
    val tagline: String,
    val release_date: String,
    @Json(name = "poster_path") val posterPath: String?,
    val backdrop_path: String?,
    val adult: Boolean,
    val original_language: String,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int
)