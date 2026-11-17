package com.example.movie_catalog.ui.common

sealed interface UiState<out T> {
    data object OnLoading : UiState<Nothing>
    data class OnSuccess<T>(val data : T) : UiState<T>
    data class OnError(val message : String) : UiState<Nothing>
}

data class ListUiState<T>(
    val items : List<T> = emptyList(),
    val isLoading  : Boolean = false,
    val error : String? = null,
    val endReached : Boolean = false,
)