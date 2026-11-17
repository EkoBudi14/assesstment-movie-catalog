package com.example.movie_catalog.data.remote

import com.example.movie_catalog.data.remote.response.GenreResponse
import com.example.movie_catalog.data.remote.response.MovieDetailResponse
import com.example.movie_catalog.data.remote.response.MovieResponse
import com.example.movie_catalog.data.remote.response.ReviewResponse
import com.example.movie_catalog.data.remote.response.VideoResponse
import com.example.movie_catalog.domain.model.PagedData
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    suspend fun getGenreMovie(): GenreResponse

    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") movieId: Int): MovieDetailResponse

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") movieId: Int,
        @Query("page") page: Int
    ) : ReviewResponse

    @GET("movie/{id}/videos")
    suspend fun getVideos(
        @Path("id") movieId: Int
    ): VideoResponse
}