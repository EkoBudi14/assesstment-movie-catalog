package com.example.movie_catalog.ui.genre

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.LocalPolice
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movie_catalog.domain.model.Genre
import com.example.movie_catalog.ui.common.LoadingView
import com.example.movie_catalog.ui.common.MessageView
import com.example.movie_catalog.ui.common.UiState
import com.example.movie_catalog.ui.components.AppTopBar


@Composable
fun GenreScreen(
    onGenreClick: (Genre) -> Unit,
    viewModel: GenreViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "Genres") }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val state = uiState) {
                is UiState.OnLoading -> LoadingView()
                is UiState.OnError -> MessageView(
                    message = state.message,
                    onRetry = viewModel::loadGenres
                )

                is UiState.OnSuccess -> GenreGrid(genres = state.data, onGenreClick = onGenreClick)
            }
        }
    }
}

@Composable
private fun GenreGrid(genres: List<Genre>, onGenreClick: (Genre) -> Unit) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(genres, key = { it.id }) { genre ->
            GenreCard(genre = genre, onClick = { onGenreClick(genre) })
        }
    }

}


@Composable
fun GenreCard(genre: Genre, onClick: () -> Unit) {
    val accent = genreColor(genre.id)

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = accent.copy(alpha = 0.15f)),
        border = BorderStroke(1.dp, accent.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accent.copy(alpha = 0.25f))
            ) {
                Icon(
                    imageVector = genreIcon(genre.id),
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(
                text = genre.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

    }
}

private fun genreIcon(genreId: Int): ImageVector = when (genreId) {
    28 -> Icons.Outlined.Bolt
    12 -> Icons.Outlined.Explore
    16 -> Icons.Outlined.Palette
    35 -> Icons.Outlined.SentimentVerySatisfied
    80 -> Icons.Outlined.LocalPolice
    99 -> Icons.Outlined.Videocam
    18 -> Icons.Outlined.TheaterComedy
    10751 -> Icons.Outlined.FamilyRestroom
    14 -> Icons.Outlined.AutoAwesome
    36 -> Icons.Outlined.HistoryEdu
    27 -> Icons.Outlined.DarkMode
    10402 -> Icons.Outlined.MusicNote
    9648 -> Icons.Outlined.Search
    10749 -> Icons.Outlined.Favorite
    878 -> Icons.Outlined.RocketLaunch
    10770 -> Icons.Outlined.Tv
    53 -> Icons.Outlined.Visibility
    10752 -> Icons.Outlined.MilitaryTech
    37 -> Icons.Outlined.Landscape
    else -> Icons.Outlined.Movie
}

private fun genreColor(genreId: Int): Color = when (genreId) {
    28, 53, 10752 -> Color(0xFFE5533D)
    12, 37 -> Color(0xFFEF9F27)
    16, 10751 -> Color(0xFF7F77DD)
    35, 10402 -> Color(0xFFF2C94C)
    80, 9648 -> Color(0xFF5DA9E9)
    99, 36 -> Color(0xFF97C459)
    18, 10749 -> Color(0xFFED93B1)
    14, 878 -> Color(0xFF1D9E75)
    27 -> Color(0xFFB4B2A9)
    else -> Color(0xFFD85A30)
}