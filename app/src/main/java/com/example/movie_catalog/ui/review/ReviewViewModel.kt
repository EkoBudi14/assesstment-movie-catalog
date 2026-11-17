package com.example.movie_catalog.ui.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movie_catalog.domain.Resource
import com.example.movie_catalog.domain.model.Review
import com.example.movie_catalog.domain.usecase.GetReviewUseCase
import com.example.movie_catalog.ui.common.ListUiState
import com.example.movie_catalog.ui.navigation.ReviewRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getReviews: GetReviewUseCase,
    saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = saveStateHandle.toRoute<ReviewRoute>().movieId
    private val _state = MutableStateFlow(ListUiState<Review>())
    val state: StateFlow<ListUiState<Review>> = _state.asStateFlow()


    private var currentPage = 0

    init {
        initLoader()
    }

    fun initLoader() {
        val state = _state.value
        if (state.isLoading || state.endReached) return

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            when (val result = getReviews(movieId, currentPage + 1)) {
                is Resource.Success -> {
                    currentPage = result.data.page
                    _state.update {
                        it.copy(
                            items = (it.items + result.data.items).distinctBy { review -> review.id },
                            isLoading = false,
                            endReached = currentPage >= result.data.totalPages
                        )
                    }
                }

                is Resource.Error -> _state.update {
                    it.copy(isLoading = false, error = result.message)
                }
            }
        }
    }
}