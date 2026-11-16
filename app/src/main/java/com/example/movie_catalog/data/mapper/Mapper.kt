package com.example.movie_catalog.data.mapper

import com.example.movie_catalog.data.remote.response.GenreItem
import com.example.movie_catalog.data.remote.response.MovieItem
import com.example.movie_catalog.data.remote.response.MovieResponse
import com.example.movie_catalog.domain.model.Genre
import com.example.movie_catalog.domain.model.Movie
import com.example.movie_catalog.domain.model.PagedData


fun GenreItem.toDomain() = Genre(
    id = id,
    name = name,
)

fun MovieItem.toDomain() = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    rating = voteAverage
)

fun MovieResponse.toDomain() = PagedData(
    page = page,
    totalPages = totalPages,
    items = result.map { it.toDomain() }
)