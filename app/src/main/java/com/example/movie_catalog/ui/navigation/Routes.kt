package com.example.movie_catalog.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object GenreRoute

@Serializable
data class MovieListRoute(val genreId: Int, val genreName: String)

@Serializable
data class DetailRoute(val movieId: Int)

@Serializable
data class ReviewRoute(val movieId: Int)