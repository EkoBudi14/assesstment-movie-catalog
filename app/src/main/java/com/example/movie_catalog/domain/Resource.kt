package com.example.movie_catalog.domain


sealed class Resource<out T> {
    data class Success<T>(val data : T) : Resource<T>()
    data class Error(val message : String) : Resource<Nothing>()
}