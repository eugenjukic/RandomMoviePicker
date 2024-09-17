package com.example.randommoviepicker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randommoviepicker.model.SavedMovie

@Database(entities = [SavedMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
