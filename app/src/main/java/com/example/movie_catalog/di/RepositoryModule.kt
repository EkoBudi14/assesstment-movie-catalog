package com.example.movie_catalog.di

import com.example.movie_catalog.data.MovieRepositoryImpl
import com.example.movie_catalog.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindMovieRepository(impl : MovieRepositoryImpl) : MovieRepository
}