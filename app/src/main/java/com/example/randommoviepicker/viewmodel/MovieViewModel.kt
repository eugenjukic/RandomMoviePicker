package com.example.randommoviepicker.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randommoviepicker.data.MovieDao
import com.example.randommoviepicker.data.MovieDatabaseProvider
import com.example.randommoviepicker.model.Movie
import com.example.randommoviepicker.model.SavedMovie
import com.example.randommoviepicker.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: MovieRepository

    init {
        val movieDao = MovieDatabaseProvider.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
    }


    var movieList = mutableStateOf<List<Movie>>(emptyList())
        private set

    var isLoading = mutableStateOf(true)
        private set

    var errorMessage = mutableStateOf<String?>(null)
    private set

    var selectedRandomMovie = mutableStateOf<Movie?>(null)
    private set

    var selectedMovieDetails = mutableStateOf<Movie?>(null)
    private set

    var isLoadingDetails = mutableStateOf(false)
        private set

    var detailsErrorMessage = mutableStateOf<String?>(null)
    private set

    private var hasFetchedDetails = false // Add this flag to control fetching
    private var currentMovieId: Int? = null

    init {
        // Automatically fetch the "Popular" movies when the ViewModel is created
        fetchMoviesByCategory("popular")
    }

    fun fetchMoviesByCategory(category: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMoviesByCategory(category)
                withContext(Dispatchers.Main) {
                    movieList.value = response.results
                    isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLoading.value = false
                    errorMessage.value = e.message
                }
            }
        }
    }
    // Function to pick a random movie
    fun pickRandomMovie() {
        if (movieList.value.isNotEmpty()) {
            selectedRandomMovie.value = movieList.value.random() // Pick a random movie from the list
        }
    }

    // Function to clear the selected movie (when the dialog is closed)
    fun clearRandomMovie() {
        selectedRandomMovie.value = null
    }
    fun fetchMovieDetails(movieId: Int) {
        // Check if the movie ID is the same as the current one to avoid refetching
        if (currentMovieId == movieId || isLoadingDetails.value || selectedMovieDetails.value != null) {
            Log.d("MovieViewModel", "Skipping API call, already loading or same movie")
            return
        }

        currentMovieId = movieId // Set the current movie ID
        hasFetchedDetails = true
        isLoadingDetails.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getMovieDetails(movieId)
                withContext(Dispatchers.Main) {
                    selectedMovieDetails.value = response
                    isLoadingDetails.value = false
                    Log.d("MovieViewModel", "Movie details fetched successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    isLoadingDetails.value = false
                    detailsErrorMessage.value = e.message
                    Log.e("MovieViewModel", "Error fetching movie details: ${e.message}")
                }
            }
        }
    }

    fun clearMovieDetails() {
        currentMovieId = null // Reset the current movie ID
        selectedMovieDetails.value = null
        isLoadingDetails.value = false
        detailsErrorMessage.value = null
        hasFetchedDetails = false
    }


    // Save a movie
    fun saveMovie(movie: SavedMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveMovie(movie)
        }
    }

    // Get all saved movies
    fun getAllSavedMovies(): Flow<List<SavedMovie>> {
        return repository.getAllSavedMovies()
    }

    // Delete a saved movie
    fun deleteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movieId)
        }
    }

}
