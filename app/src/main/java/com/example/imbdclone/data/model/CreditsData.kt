package com.example.imbdclone.data.model

data class CreditsData(
    val cast: ArrayList<Cast>
)

data class Cast(
    val name: String,
    val profile_path: String,
    val character: String
)