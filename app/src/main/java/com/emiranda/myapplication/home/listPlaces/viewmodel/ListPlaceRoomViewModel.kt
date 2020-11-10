package com.emiranda.myapplication.home.listPlaces.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emiranda.myapplication.home.listPlaces.model.RemoteLocalRepository
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListPlaceRoomViewModel (private val repo : RemoteLocalRepository) : ViewModel(){


    val emptyList : LiveData<Int> = repo.getItemsCount()

    var places : LiveData<MutableList<Place>> = repo.getAllPlaces()


    fun getRoomData(){
        try {
            viewModelScope.launch {
                repo.SyncFirebaseWithRoom()
            }
        }catch (e: Exception){
            Log.d("REMOTE_VIEWMODEL",e.localizedMessage)
        }
    }

}