package com.ramand.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramand.topmovies.models.register.BodyRegister
import com.ramand.topmovies.models.register.ResponseRegister
import com.ramand.topmovies.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {

    val registerUser = MutableLiveData<ResponseRegister>()
    val loading = MutableLiveData<Boolean>()

    fun sendRegisterUser(body: BodyRegister) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.registerUser(body)
        if (response.isSuccessful){
            registerUser.postValue(response.body())
        }
        loading.postValue(false)
    }
}

