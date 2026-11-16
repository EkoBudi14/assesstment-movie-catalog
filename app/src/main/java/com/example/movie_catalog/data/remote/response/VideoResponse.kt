package com.example.movie_catalog.data.remote.response


data class VideoResponse(
    val results: List<VideoItem>
)

data class VideoItem(
    val key: String,
    val site: String,
    val type: String
)