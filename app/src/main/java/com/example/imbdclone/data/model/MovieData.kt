package com.example.imbdclone.data.model

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<MovieData>
)

data class MovieData(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "is_favorite") val isFavorite: Boolean = false,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int
)

