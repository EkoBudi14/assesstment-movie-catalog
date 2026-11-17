package com.example.movie_catalog.domain.usecase

import com.example.movie_catalog.domain.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId : Int) = repository.getMovieDetail(movieId)
}