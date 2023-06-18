package com.ramand.topmovies.repository

import com.ramand.topmovies.api.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: ApiServices) {
    suspend fun topMoviesList(id: Int) = api.moviesTopList(id)
    suspend fun genresList() = api.genresList()
    suspend fun lastMoviesList() = api.moviesLastList()
}