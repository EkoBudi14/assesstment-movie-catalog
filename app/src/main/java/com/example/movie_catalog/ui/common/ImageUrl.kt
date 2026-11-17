package com.example.movie_catalog.ui.common

import com.example.movie_catalog.BuildConfig

fun imageUrl(path: String?): String? = path?.let { BuildConfig.IMAGE_URL + it }