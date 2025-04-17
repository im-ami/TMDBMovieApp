package com.example.imbdclone.data.model

import com.squareup.moshi.Json

data class MovieResponse(
    val page: Int,
    val results: List<MovieData>
)

data class MovieData(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    val backdrop_path: String,
    val adult: Boolean,
    val original_language: String,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int
)
