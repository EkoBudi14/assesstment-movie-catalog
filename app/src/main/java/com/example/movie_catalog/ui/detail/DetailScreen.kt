package com.example.movie_catalog.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movie_catalog.domain.model.MovieDetail
import com.example.movie_catalog.ui.common.LoadingView
import com.example.movie_catalog.ui.common.MessageView
import com.example.movie_catalog.ui.common.TrailerPlayer
import com.example.movie_catalog.ui.common.UiState
import com.example.movie_catalog.ui.common.imageUrl
import com.example.movie_catalog.ui.components.AppTopBar

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    onReviewsClick: (Int) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val trailerKey by viewModel.trailerKey.collectAsStateWithLifecycle()
    val title = (uiState as? UiState.OnSuccess)?.data?.title ?: "Detail"


    Scaffold(
        topBar = { AppTopBar(title = title, onBack = onBack) }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            when (val state = uiState) {
                is UiState.OnLoading -> LoadingView()
                is UiState.OnError -> MessageView(
                    message = state.message,
                    onRetry = viewModel::loadDetail
                )

                is UiState.OnSuccess -> DetailContent(
                    movie = state.data,
                    trailerKey = trailerKey,
                    onReviewsClick = { onReviewsClick(state.data.id) }
                )
            }
        }
    }
}


@Composable
fun DetailContent(
    movie: MovieDetail,
    trailerKey: String?,
    onReviewsClick: () -> Unit
) {
    val uriHandle = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = imageUrl(movie.backdropPath),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = imageUrl(movie.posterPath),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
                Text(text = "★ %.1f".format(movie.rating), modifier = Modifier.padding(top = 4.dp))
                movie.releaseDate?.let { Text(text = it) }
                movie.runtime?.let { Text(text = "$it min") }
                Text(
                    text = movie.genres.joinToString { it.name },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Text(
            text = movie.overview.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (trailerKey != null) {
            TrailerPlayer(
                videoId = trailerKey,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .aspectRatio(16f / 9f)
            )
            TextButton(
                onClick = { uriHandle.openUri("https://www.youtube.com/watch?v=$trailerKey") },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Open in YouTube")
            }
        } else {
            Text(
                text = "Trailer not available",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
        }

        OutlinedButton(
            onClick = onReviewsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("See Reviews")
        }


    }

}
