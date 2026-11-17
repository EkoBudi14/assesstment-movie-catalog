package com.example.movie_catalog.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movie_catalog.domain.model.Review
import com.example.movie_catalog.ui.common.LoadMoreFooter
import com.example.movie_catalog.ui.common.LoadingView
import com.example.movie_catalog.ui.common.MessageView
import com.example.movie_catalog.ui.common.OnBottomReached
import com.example.movie_catalog.ui.components.AppTopBar


@Composable
fun ReviewScreen(
    onBack: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    listState.OnBottomReached(onLoadMore = viewModel::initLoader)


    Scaffold(topBar = { AppTopBar(title = "Reviews", onBack = onBack) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            val state = uiState
            when {
                state.items.isEmpty() && state.isLoading -> LoadingView()
                state.items.isEmpty() && state.error != null ->
                    MessageView(message = state.error, onRetry = viewModel::initLoader)

                state.items.isEmpty() && state.endReached -> MessageView(message = "No reviews yet")
                else -> LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.items, key = { it.id }) { review ->
                        ReviewItem(review = review)
                    }
                    item {
                        LoadMoreFooter(
                            isLoading = state.isLoading,
                            error = state.error,
                            onRetry = viewModel::initLoader
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun ReviewItem(review: Review) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = review.author, style = MaterialTheme.typography.titleSmall)
            Text(text = review.createdAt.take(10), style = MaterialTheme.typography.bodySmall)
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 8,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}