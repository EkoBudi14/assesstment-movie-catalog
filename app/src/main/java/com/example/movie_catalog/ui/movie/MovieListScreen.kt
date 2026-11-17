package com.example.movie_catalog.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.movie_catalog.ui.common.OnBottomReached
import com.example.movie_catalog.ui.common.RatingBadge
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
    val gridState = rememberLazyGridState()
    gridState.OnBottomReached(onLoadMore = viewModel::loadMore)


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

                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Box {
            AsyncImage(
                model = imageUrl(movie.posterPath),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            RatingBadge(
                rating = movie.rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        }
        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = movie.releaseDate.take(4).orEmpty(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}