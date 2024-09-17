package com.example.randommoviepicker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randommoviepicker.model.SavedMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Insert method with correct return type (Room expects long if it's a single entity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: SavedMovie): Long

    // Fetch all saved movies as a flow
    @Query("SELECT * FROM saved_movies")
    fun getAllSavedMovies(): Flow<List<SavedMovie>>

    // Delete a movie method with correct return type (int indicates how many rows were deleted)
    @Query("DELETE FROM saved_movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int): Int
}
