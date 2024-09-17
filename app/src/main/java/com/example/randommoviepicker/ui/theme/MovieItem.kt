package com.example.randommoviepicker.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randommoviepicker.model.Movie

@Composable
fun MovieItem(navController: NavController, movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                // Navigate to the movie details screen with the movie's ID
                navController.navigate("movie_detail/${movie.id}")
            }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(15.dp))
                .padding(4.dp)
        )
        // Movie Title
        Text(
            text = movie.title,
            modifier = Modifier
                .padding(bottom = 4.dp)
                ,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )

        // Movie Overview
        Text(
            text = movie.overview,
            modifier = Modifier.padding(bottom = 8.dp),
            maxLines = 3,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        // You can optionally display the movie poster or a smaller image here

    }
}
