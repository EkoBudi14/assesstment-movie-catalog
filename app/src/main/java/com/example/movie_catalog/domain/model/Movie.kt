package com.example.movie_catalog.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val rating: Double,
)