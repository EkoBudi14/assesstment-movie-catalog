package com.example.movie_catalog.data.remote.response

data class GenreResponse(
    val genres: List<GenreItem>
)

data class GenreItem(
    val id: Int,
    val name: String
)