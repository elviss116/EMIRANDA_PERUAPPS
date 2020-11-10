package com.emiranda.myapplication.home.listPlaces.model

import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import com.emiranda.myapplication.util.MResource
import kotlinx.coroutines.flow.Flow

interface ListPlaceDataSource {
    suspend fun getPlaces() : Flow<MResource<MutableList<Place>>>
}