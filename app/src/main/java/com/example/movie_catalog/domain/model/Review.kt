package com.example.movie_catalog.domain.model

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val createdAt: String
)