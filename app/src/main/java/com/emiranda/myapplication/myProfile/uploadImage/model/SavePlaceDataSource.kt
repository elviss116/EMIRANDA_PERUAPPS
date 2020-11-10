package com.emiranda.myapplication.myProfile.uploadImage.model

import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place

interface SavePlaceDataSource {
    suspend fun savePlace(place: Place) : String
}