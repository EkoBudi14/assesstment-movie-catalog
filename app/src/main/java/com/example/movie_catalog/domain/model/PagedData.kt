package com.example.movie_catalog.domain.model

data class PagedData<T>(
    val page: Int,
    val totalPages: Int,
    val items: List<T>
)