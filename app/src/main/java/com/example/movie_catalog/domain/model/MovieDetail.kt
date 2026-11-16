package com.example.movie_catalog.domain.model

data class MovieDetail (
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val rating: Double,
    val runtime: Int?,
    val genres: List<Genre>
)