package com.example.movie_catalog.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie_catalog.domain.Resource
import com.example.movie_catalog.domain.model.MovieDetail
import com.example.movie_catalog.domain.usecase.GetMovieDetailUseCase
import com.example.movie_catalog.domain.usecase.GetTrilerKeyUseCase
import com.example.movie_catalog.ui.common.UiState
import com.example.movie_catalog.ui.navigation.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetailUseCase,
    private val getTrailerKey: GetTrilerKeyUseCase,
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: Int = saveStateHandle.toRoute<DetailRoute>().movieId

    private val _state = MutableStateFlow<UiState<MovieDetail>>(UiState.OnLoading)
    val state: StateFlow<UiState<MovieDetail>> = _state.asStateFlow()

    private val _triailerKey = MutableStateFlow<String?>(null)
    val trailerKey: StateFlow<String?> = _triailerKey.asStateFlow()


    init {
        loadDetail()
    }

    fun loadDetail() {
        _state.value = UiState.OnLoading
        viewModelScope.launch {
            _state.value = when (val result = getMovieDetail(movieId)) {
                is Resource.Success -> UiState.OnSuccess(result.data)
                is Resource.Error -> UiState.OnError(result.message)
            }
        }

        viewModelScope.launch {
            _triailerKey.value = (getTrailerKey(movieId) as? Resource.Success)?.data
        }
    }

}

