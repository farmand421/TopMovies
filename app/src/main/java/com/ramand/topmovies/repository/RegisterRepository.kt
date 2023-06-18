package com.ramand.topmovies.repository

import com.ramand.topmovies.api.ApiServices
import com.ramand.topmovies.models.register.BodyRegister
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api:ApiServices) {
    suspend fun registerUser(body: BodyRegister) = api.registerUser(body)
}