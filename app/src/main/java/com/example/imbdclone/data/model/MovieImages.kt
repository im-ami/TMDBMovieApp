package com.example.imbdclone.data.model

data class MovieImages(
    val backdrops: List<MoviePosters>
)

data class MoviePosters(
    val file_path: String
)