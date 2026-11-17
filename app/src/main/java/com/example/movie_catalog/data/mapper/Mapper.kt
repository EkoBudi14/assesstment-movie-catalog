package com.example.movie_catalog.data.mapper

import com.example.movie_catalog.data.remote.response.GenreItem
import com.example.movie_catalog.data.remote.response.MovieDetailResponse
import com.example.movie_catalog.data.remote.response.MovieItem
import com.example.movie_catalog.data.remote.response.MovieResponse
import com.example.movie_catalog.data.remote.response.ReviewItem
import com.example.movie_catalog.data.remote.response.ReviewResponse
import com.example.movie_catalog.domain.model.Genre
import com.example.movie_catalog.domain.model.Movie
import com.example.movie_catalog.domain.model.MovieDetail
import com.example.movie_catalog.domain.model.PagedData
import com.example.movie_catalog.domain.model.Review


fun GenreItem.toDomain() = Genre(
    id = id,
    name = name,
)

fun MovieItem.toDomain() = Movie(
    id = id,
    title = title,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate,
    rating = voteAverage
)

fun MovieResponse.toDomain() = PagedData(
    page = page,
    totalPages = totalPages,
    items = results.map { it.toDomain() }
)

fun MovieDetailResponse.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    rating = voteAverage,
    runtime = runtime,
    genres = genres.map { it.toDomain() }
)

fun ReviewItem.toDomain() = Review(
    id = id,
    author = author,
    content = content,
    createdAt = createdAt
)

fun ReviewResponse.toDomain() = PagedData(
    page = page,
    totalPages = totalPages,
    items = results.map { it.toDomain() }
)