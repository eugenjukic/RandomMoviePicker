package com.example.randommoviepicker.network

import com.example.randommoviepicker.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.randommoviepicker.model.MovieResponse
import retrofit2.http.Path

const val API_KEY = "e3c6db64adca6172e9b2697b21b9f405" // Replace with your actual API key
const val BASE_URL = "https://api.themoviedb.org/3/"

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "credits"
    ): Movie
}
