package com.example.imbdclone.data.model
import com.squareup.moshi.Json

data class CreditsData(
    @Json(name = "id") val id: Int,
    @Json(name = "cast") val cast: List<Cast>
)

data class Cast(
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "character") val character: String
)
