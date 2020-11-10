package com.emiranda.myapplication.util.workmanager.model

import com.emiranda.myapplication.util.workmanager.mObject.UserApp

interface GPSWorkerDataSource {

    suspend fun saveUserLocation(userApp: UserApp)
}