package com.emiranda.myapplication.home.listPlaces.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.emiranda.myapplication.database.dao.PLACE_DAO
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import com.emiranda.myapplication.util.MResource
import kotlinx.coroutines.flow.collect

class RemoteLocalRepository(val dao: PLACE_DAO, val repo : ListPlaceDataSource) {

    suspend fun SyncFirebaseWithRoom() {
        try {
            repo.getPlaces().collect {
                it.let {
                    when(it){
                        is MResource.Success -> {
                            dao.deleteAllPlace()
                            dao.insertPlace(it.data!!)
                        }
                    }
                }
            }
        }catch (e: Exception){
            Log.d("REMOTE_REPO",e.localizedMessage)
        }
    }

    fun getAllPlaces(): LiveData<MutableList<Place>>{
        return dao.getAllPlace()
    }

    fun getItemsCount(): LiveData<Int>{
        return dao.getItemsCount()
    }
}