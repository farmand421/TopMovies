package com.ramand.topmovies.repository

import com.ramand.topmovies.api.ApiServices
import com.ramand.topmovies.db.MovieDao
import com.ramand.topmovies.db.MovieEntity
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices , private val dao: MovieDao) {
    //Api
    suspend fun detailMovie(id: Int) = api.detailMovie(id)

    //Database
    suspend fun insertMovie(entity: MovieEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MovieEntity) = dao.deleteMovie(entity)
    suspend fun existsMovie(id: Int) = dao.existsMovie(id)
}