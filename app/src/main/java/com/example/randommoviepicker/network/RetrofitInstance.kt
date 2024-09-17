package com.example.randommoviepicker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // Create the Retrofit instance
    val api: MovieService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter to parse JSON
            .build()
            .create(MovieService::class.java)
    }
}
