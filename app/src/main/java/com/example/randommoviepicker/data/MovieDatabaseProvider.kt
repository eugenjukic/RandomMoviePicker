package com.example.randommoviepicker.data

import android.content.Context
import androidx.room.Room

object MovieDatabaseProvider {
    @Volatile
    private var INSTANCE: MovieDatabase? = null

    fun getDatabase(context: Context): MovieDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
