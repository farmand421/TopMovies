package com.ramand.topmovies.repository

import com.ramand.topmovies.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api:ApiServices) {
    suspend fun searchMovie(name: String) = api.searchMovie(name)
}