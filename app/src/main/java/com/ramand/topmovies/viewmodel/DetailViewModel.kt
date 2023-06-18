package com.ramand.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramand.topmovies.db.MovieEntity
import com.ramand.topmovies.models.detail.ResponseDetail
import com.ramand.topmovies.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository): ViewModel(){

    //Api
    val detailMovie = MutableLiveData<ResponseDetail>()
    val loading = MutableLiveData<Boolean>()

    fun loadingDetailMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.detailMovie(id)
        if (response.isSuccessful)(
                detailMovie.postValue(response.body())
        )
        loading.postValue(false)
    }
    //Database
    val isFavorite = MutableLiveData<Boolean>()
    suspend fun existsMovie(id: Int) = withContext(viewModelScope.coroutineContext){repository.existsMovie(id)}

    fun favoriteMovie(id: Int , entity: MovieEntity) = viewModelScope.launch {
        val exists = repository.existsMovie(id)
        if (exists){
            isFavorite.postValue(false)
            repository.deleteMovie(entity)
        }else{
            isFavorite.postValue(true)
            repository.insertMovie(entity)
        }
    }
}