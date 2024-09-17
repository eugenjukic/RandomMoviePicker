package com.example.randommoviepicker.ui.theme

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randommoviepicker.model.Movie
import com.example.randommoviepicker.model.SavedMovie
import com.example.randommoviepicker.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(navController: NavController, viewModel: MovieViewModel, movieId: String?) {
    val movieIdInt = movieId?.toIntOrNull()
    LaunchedEffect(key1 = movieIdInt) {
        if (movieIdInt != null && viewModel.selectedMovieDetails.value == null) {
            Log.d("MovieDetailScreen", "Fetching movie details for movieId: $movieIdInt")
            viewModel.fetchMovieDetails(movieIdInt)
        }
    }

    val movieDetails by viewModel.selectedMovieDetails
    val isLoading by viewModel.isLoadingDetails
    val errorMessage by viewModel.detailsErrorMessage


    Log.d("MovieDetailScreen", "Current state - isLoading: $isLoading, movieDetails: $movieDetails")
    BackHandler {
        viewModel.clearMovieDetails()
        navController.popBackStack()
    }

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(onClick = {
                viewModel.saveMovie(
                    SavedMovie(
                        id = movieDetails!!.id,
                        title = movieDetails!!.title,
                        posterPath = movieDetails!!.poster_path,
                        overview = movieDetails!!.overview,
                        rating = movieDetails!!.vote_average,
                        releaseDate = movieDetails!!.release_date
                    )
                )
            }) {
                Text("Save Movie")
            }
        }
        // Back button that navigates back to the previous screen

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator() // Show loading spinner while fetching details
                }
                errorMessage != null -> {
                    Text(text = "Error: $errorMessage") // Show error message if something went wrong
                }
                movieDetails != null -> {
                    MovieDetailsContent(movieDetails = movieDetails!!)
                }
                else -> {
                    Text("Movie details not available")
                }
            }
        }

    }
}

@Composable
fun MovieDetailsContent(movieDetails: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .clip(RoundedCornerShape(15.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Movie poster
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDetails.poster_path}",
            contentDescription = movieDetails.title,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Movie title
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary),
            text = movieDetails.title, style = MaterialTheme.typography.titleLarge)

        // Movie runtime
        Text(text = "Duration: ${movieDetails.runtime} min")

        // Movie rating
        Text(text = "Rating: ${movieDetails.vote_average}/10")

        // Movie genres (categories)
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary),
            text = "Category: ${movieDetails.genres.joinToString(", ") { it.name }}")

        // Movie plot
        Text(text = "Plot: ${movieDetails.overview}")

        Spacer(modifier = Modifier.height(16.dp))


        // Check if credits or cast is null before accessing them
        if (movieDetails.credits?.cast != null) {
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                text = "Cast:")
            Column {
                movieDetails.credits.cast.forEach { castMember ->
                    Text(text = "${castMember.name} as ${castMember.character}")
                }
            }
        } else {
            Text(text = "No cast information available.")
        }
    }
}
