package com.example.imbdclone.data.model

import com.squareup.moshi.Json

data class MovieImages(
    @Json(name = "backdrops") val backdrops: List<MoviePosters>
)

data class MoviePosters(
    @Json(name = "file_path") val filePath: String
)
