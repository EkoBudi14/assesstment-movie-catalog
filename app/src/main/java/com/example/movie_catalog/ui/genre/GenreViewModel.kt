package com.example.movie_catalog.ui.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.domain.Resource
import com.example.movie_catalog.domain.model.Genre
import com.example.movie_catalog.domain.usecase.GetGenreUseCase
import com.example.movie_catalog.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val genreUseCase: GetGenreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Genre>>>(UiState.OnLoading)
    val state: StateFlow<UiState<List<Genre>>> = _state.asStateFlow()


    init {
        loadGenres()
    }

    fun loadGenres() {
        _state.value = UiState.OnLoading
        viewModelScope.launch {
            _state.value = when (val result = genreUseCase()) {
                is Resource.Success -> UiState.OnSuccess(result.data)
                is Resource.Error -> UiState.OnError(result.message)
            }
        }
    }

}