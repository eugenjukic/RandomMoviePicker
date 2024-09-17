package com.example.randommoviepicker.repository

import com.example.randommoviepicker.data.MovieDao
import com.example.randommoviepicker.model.Movie
import com.example.randommoviepicker.model.MovieResponse
import com.example.randommoviepicker.model.SavedMovie
import com.example.randommoviepicker.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow


class MovieRepository (private val movieDao: MovieDao){
    suspend fun getMoviesByCategory(category: String): MovieResponse {
        return when (category) {
            "popular" -> RetrofitInstance.api.getPopularMovies("e3c6db64adca6172e9b2697b21b9f405")
            "upcoming" -> RetrofitInstance.api.getUpcomingMovies("e3c6db64adca6172e9b2697b21b9f405")
            "top_rated" -> RetrofitInstance.api.getTopRatedMovies("e3c6db64adca6172e9b2697b21b9f405")
            else -> RetrofitInstance.api.getPopularMovies("e3c6db64adca6172e9b2697b21b9f405")
        }
    }
    suspend fun getMovieDetails(movieId: Int): Movie {
        return RetrofitInstance.api.getMovieDetails(movieId, "e3c6db64adca6172e9b2697b21b9f405")
    }

    suspend fun saveMovie(movie: SavedMovie): Long {
        return movieDao.saveMovie(movie)
    }

    fun getAllSavedMovies(): Flow<List<SavedMovie>> {
        return movieDao.getAllSavedMovies()
    }

    suspend fun deleteMovie(movieId: Int): Int {
        return movieDao.deleteMovie(movieId)
    }

}
