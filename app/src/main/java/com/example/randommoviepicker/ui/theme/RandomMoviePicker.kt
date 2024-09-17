package com.example.randommoviepicker.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randommoviepicker.model.Movie
import com.example.randommoviepicker.viewmodel.MovieViewModel

@Composable
fun RandomMoviePicker(navController: NavController, viewModel: MovieViewModel) {
    val movies by viewModel.movieList
    val selectedMovie by viewModel.selectedRandomMovie

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(
            onClick = {
                viewModel.pickRandomMovie() // Pick a random movie
            },

        ) {
            Text("Random Movie")
        }

        Button(onClick = { navController.navigate("savedMoviesScreen") }) {
            Text("View Saved Movies")
        }

        // If a movie is selected, show the dialog
        selectedMovie?.let { movie ->
            RandomMovieDialog(movie = movie, onDismiss = { viewModel.clearRandomMovie() })
        }
    }
}

@Composable
fun RandomMovieDialog(movie: Movie, onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = movie.title)
        },
        text = {
            Column {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.overview)
            }
        },
        confirmButton = {
            androidx.compose.material3.Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
