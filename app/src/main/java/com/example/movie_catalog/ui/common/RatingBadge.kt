package com.example.movie_catalog.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RatingBadge(rating: Double, modifier: Modifier = Modifier) {
    Text(
        text = "★ %.1f".format(rating),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onTertiary,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}