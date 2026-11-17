package com.example.movie_catalog.ui.review

import androidx.lifecycle.ViewModel
import com.example.movie_catalog.domain.usecase.GetReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getReviews: GetReviewUseCase,
) : ViewModel() {
    
}