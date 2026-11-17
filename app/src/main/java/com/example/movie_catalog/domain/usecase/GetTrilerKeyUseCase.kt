package com.example.movie_catalog.domain.usecase

import com.example.movie_catalog.domain.MovieRepository
import javax.inject.Inject

class GetTrilerKeyUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId : Int) = repository.getTrailerKey(movieId)
}