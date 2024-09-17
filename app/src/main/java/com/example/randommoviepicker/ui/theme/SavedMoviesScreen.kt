package com.example.randommoviepicker.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randommoviepicker.model.SavedMovie
import com.example.randommoviepicker.viewmodel.MovieViewModel

@Composable
fun SavedMoviesScreen(viewModel: MovieViewModel) {
    // Collect saved movies from the ViewModel
    val savedMovies by viewModel.getAllSavedMovies().collectAsState(initial = emptyList())

    // Display saved movies in a LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(savedMovies) { movie ->
            MovieItem(
                movie = movie,
                onDeleteClick = { viewModel.deleteMovie(movie.id) }
            )
        }
    }
}

@Composable
fun MovieItem(movie: SavedMovie, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )
        Text(text = movie.title)
        Text(text = movie.overview)
        Text(text = "Rating: ${movie.rating}/10")
        Text(text = "Release Date: ${movie.releaseDate}")

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Movie")
        }
    }
}
