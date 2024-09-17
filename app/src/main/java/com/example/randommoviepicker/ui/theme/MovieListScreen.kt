package com.example.randommoviepicker.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.randommoviepicker.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(navController: NavController, viewModel: MovieViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // CategorySelector allows users to select categories like Popular, Upcoming, etc.
        CategorySelector(viewModel = viewModel)

        // "Pick a Random Movie" button
        RandomMoviePicker(viewModel = viewModel, navController = navController)

        // MovieListContent shows the list of movies or a loading spinner, and handles errors
        MovieListContent(navController = navController, viewModel = viewModel)

    }
}

@Composable
fun CategorySelector(viewModel: MovieViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { viewModel.fetchMoviesByCategory("popular") }) {
            Text("Popular")
        }
        Button(onClick = { viewModel.fetchMoviesByCategory("upcoming") }) {
            Text("Upcoming")
        }
        Button(onClick = { viewModel.fetchMoviesByCategory("top_rated") }) {
            Text("Top Rated")
        }
    }
}

@Composable
fun MovieListContent(navController: NavController, viewModel: MovieViewModel) {
    val movies by viewModel.movieList
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator() // Show loading spinner while movies are being fetched
            }
            errorMessage != null -> {
                Text(text = "Error: $errorMessage") // Show error message if something went wrong
            }
            movies.isNotEmpty() -> {
                // Display the list of movies if available, passing the navController to handle clicks
                MovieList(navController = navController, movies = movies)
            }
            else -> {
                Text("No movies available") // Fallback if no movies are returned
            }
        }
    }
}

@Composable
fun MovieList(navController: NavController, movies: List<com.example.randommoviepicker.model.Movie>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),

    ) {
        items(movies) { movie ->
            MovieItem(navController = navController, movie = movie)
        }
    }
}
