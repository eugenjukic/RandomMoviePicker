package com.example.randommoviepicker.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val runtime: Int,
    val vote_average: Double,
    val genres: List<Genre>,
    val credits: Credits,
    val release_date: String // This should match the API response
)

data class Genre(
    val id: Int,
    val name: String
)

data class Credits(
    val cast: List<CastMember>
)

data class CastMember(
    val name: String,
    val character: String
)

