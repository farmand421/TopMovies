package com.ramand.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramand.topmovies.db.MovieEntity
import com.ramand.topmovies.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) :
    ViewModel() {

    val favoriteList = MutableLiveData<MutableList<MovieEntity>>()
    val empty = MutableLiveData<Boolean>()

    fun loadingFavoriteList() = viewModelScope.launch {
        val list = repository.allFavoriteMovies()
        if (list.isNotEmpty()) {
            favoriteList.postValue(list)
            empty.postValue(false)
        } else {
            empty.postValue(true)
        }
    }
}