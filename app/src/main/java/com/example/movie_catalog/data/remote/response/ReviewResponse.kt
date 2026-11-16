package com.example.movie_catalog.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    val page: Int,
    val results: List<ReviewItem>,
    @SerializedName("total_pages")
    val totalPages: Int
)

data class ReviewItem(
    val id: String,
    val author: String,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String
)