package com.example.imbdclone.data.model

data class CreditsData(
    val id: Int,
    val cast: List<Cast>
)

data class Cast(
    val name: String,
    val profile_path: String?,
    val character: String
)