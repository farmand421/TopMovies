package com.ramand.topmovies.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramand.topmovies.db.MovieEntity
import com.ramand.topmovies.utils.Constants

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(entity: MovieEntity)

    @Delete
    suspend fun deleteMovie(entity: MovieEntity)

    @Query("SELECT * FROM ${Constants.MOVIES_TABLE}")
    fun getAllMovies(): MutableList<MovieEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.MOVIES_TABLE} WHERE id = :movieId)")
    suspend fun existsMovie(movieId: Int): Boolean
}