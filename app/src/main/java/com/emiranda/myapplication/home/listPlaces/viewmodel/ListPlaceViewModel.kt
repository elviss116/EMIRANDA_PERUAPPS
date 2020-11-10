package com.emiranda.myapplication.home.listPlaces.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.emiranda.myapplication.home.listPlaces.model.ListPlaceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class ListPlaceViewModel(private val repository: ListPlaceDataSource) : ViewModel() {

    // NO SE USA YA QUE SE IMPLEMENTO LA PERSISTENCIA CON ROOM
    val fetchPlaces = liveData(Dispatchers.IO) {

        try {
            repository.getPlaces().collect {
                emit(it)
            }

        }catch (e: Exception){

        }
    }
}