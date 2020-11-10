package com.emiranda.myapplication.util.workmanager.model

import android.util.Log
import com.emiranda.myapplication.util.workmanager.mObject.UserApp
import com.google.firebase.firestore.FirebaseFirestore

class GPSWorkerRepository(private val firestore: FirebaseFirestore) : GPSWorkerDataSource{

    override suspend fun saveUserLocation(userApp: UserApp) {

        try {
            userApp.let {
                firestore.collection("userApp")
                    .add(it)
                    .addOnSuccessListener {
                        Log.d("REPO_SAVEUSER","item agregado ${it.id}")
                    }
                    .addOnFailureListener {
                        Log.d("REPO_SAVEUSER","error ${it.localizedMessage}")
                    }
            }
        }catch (e: Exception){
            Log.d("REPO_SAVEUSER","error ${e.localizedMessage}")
        }
    }
}