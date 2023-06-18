package com.ramand.topmovies.repository

import com.ramand.topmovies.db.MovieDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: MovieDao) {

    fun allFavoriteMovies() = dao.getAllMovies()
}