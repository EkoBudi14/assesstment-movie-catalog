package com.example.movie_catalog.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie_catalog.domain.Resource
import com.example.movie_catalog.domain.model.Movie
import com.example.movie_catalog.domain.usecase.GetMovieByGenreUseCase
import com.example.movie_catalog.ui.common.ListUiState
import com.example.movie_catalog.ui.navigation.MovieListRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesByGenre: GetMovieByGenreUseCase,
    stateSavedHandle: SavedStateHandle
) : ViewModel() {

    private val genreId: Int = stateSavedHandle.toRoute<MovieListRoute>().genreId

    private var currentPage = 0

    private val _state = MutableStateFlow(ListUiState<Movie>())

    val state: StateFlow<ListUiState<Movie>> = _state.asStateFlow()

    init {
        loadMore()
    }

    fun loadMore() {
        val state = _state.value

        if (state.isLoading || state.endReached) return

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = getMoviesByGenre(genreId, currentPage + 1)) {
                is Resource.Success -> {
                    currentPage = result.data.page
                    val lastPage = minOf(result.data.totalPages, MAX_PAGE)
                    _state.update {
                        it.copy(
                            items = (it.items + result.data.items).distinctBy { movie ->
                                movie.id
                            },
                            isLoading = false,
                            endReached = currentPage >= lastPage,
                        )
                    }
                }

                is Resource.Error -> _state.update {
                    it.copy(isLoading = false, error = result.message)
                }
            }
        }
    }


    companion object {
        private const val MAX_PAGE = 500
    }
}