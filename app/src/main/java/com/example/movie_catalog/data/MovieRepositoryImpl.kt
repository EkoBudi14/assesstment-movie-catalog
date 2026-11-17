package com.example.movie_catalog.data

import com.example.movie_catalog.data.mapper.toDomain
import com.example.movie_catalog.data.remote.ApiService
import com.example.movie_catalog.domain.MovieRepository
import com.example.movie_catalog.domain.Resource
import com.example.movie_catalog.domain.model.Genre
import com.example.movie_catalog.domain.model.Movie
import com.example.movie_catalog.domain.model.MovieDetail
import com.example.movie_catalog.domain.model.PagedData
import com.example.movie_catalog.domain.model.Review
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class MovieRepositoryImpl(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun getGenreMovie(): Resource<List<Genre>> = safeApiCall { apiService.getGenreMovie().genres.map { it.toDomain()  } }


    override suspend fun getMovieByGenre(
        genreId: Int,
        page: Int
    ): Resource<PagedData<Movie>>  = safeApiCall { apiService.getMovieByGenre(genreId, page).toDomain() }

    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetail> =  safeApiCall { apiService.getMovieDetail(movieId).toDomain() }

    override suspend fun getReviews(
        movieId: Int,
        page: Int
    ): Resource<PagedData<Review>> = safeApiCall { apiService.getReviews(movieId, page).toDomain() }

    override suspend fun getTrailerKey(movieId: Int): Resource<String?> = safeApiCall { apiService.getVideos(movieId).results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }?.key }

    private suspend fun <T> safeApiCall(call: suspend () -> T): Resource<T> =
        try {
            Resource.Success(call())
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: HttpException) {
            Resource.Error("Server error (${e.code()})")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Resource.Error("Something went wrong")
        }
}