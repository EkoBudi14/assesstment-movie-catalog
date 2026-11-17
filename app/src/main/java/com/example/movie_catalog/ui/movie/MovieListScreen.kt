package com.example.movie_catalog.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movie_catalog.domain.model.Movie
import com.example.movie_catalog.ui.common.LoadMoreFooter
import com.example.movie_catalog.ui.common.LoadingView
import com.example.movie_catalog.ui.common.MessageView
import com.example.movie_catalog.ui.common.OnButtomReached
import com.example.movie_catalog.ui.common.imageUrl
import com.example.movie_catalog.ui.components.AppTopBar

@Composable
fun MovieListScreen(
    genreName: String,
    onBack: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    listState.OnButtomReached(onLoadMore = viewModel::loadMore)


    Scaffold(topBar = { AppTopBar(title = genreName, onBack = onBack) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            val state = uiState.value

            when {
                state.items.isEmpty() && state.isLoading -> LoadingView()
                state.items.isEmpty() && state.error != null -> MessageView(
                    message = state.error.orEmpty(),
                    onRetry = viewModel::loadMore
                )

                state.items.isEmpty() && state.endReached -> MessageView(message = "No movies found")

                else -> LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.items, key = { it.id }) { movie ->
                        MovieItem(movie = movie, onClick = { onMovieClick(movie) })
                    }

                    item {
                        LoadMoreFooter(
                            isLoading = state.isLoading,
                            error = state.error,
                            onRetry = viewModel::loadMore
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = imageUrl(movie.posterPath),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.releaseDate.take(4).orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "★ %.1f".format(movie.rating),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
            }
        }
    }
}