package com.example.movie_catalog.domain

import com.example.movie_catalog.domain.model.Genre
import com.example.movie_catalog.domain.model.Movie
import com.example.movie_catalog.domain.model.MovieDetail
import com.example.movie_catalog.domain.model.PagedData
import com.example.movie_catalog.domain.model.Review

interface MovieRepository {
    suspend fun getGenreMovie() : Resource<List<Genre>>
    suspend fun getMovieByGenre(genreId : Int, page : Int) : Resource<PagedData<Movie>>
    suspend fun getMovieDetail(movieId : Int) : Result<MovieDetail>
    suspend fun getReviews(movieId : Int, page : Int) : Resource<PagedData<Review>>
    suspend fun getTrailerKey(movieId : Int) : Result<String?>
}