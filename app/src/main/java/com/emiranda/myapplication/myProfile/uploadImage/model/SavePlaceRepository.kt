package com.emiranda.myapplication.myProfile.uploadImage.model

import android.util.Log
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import com.emiranda.myapplication.util.MResource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SavePlaceRepository(private val firestore: FirebaseFirestore) : SavePlaceDataSource {

    private var msgSave = ""

    override suspend fun savePlace(place: Place) : String{

        try {
            place.let {
                val asyncProcess = firestore.collection("places")
                    .add(it)
                    .addOnSuccessListener {
                        Log.d("REPO_SPLACE","se agrego item ${it.id}")
                        msgSave = "REGISTRO EXITOSO"
                    }
                    .addOnFailureListener { exception ->
                        val messate = exception.message
                        Log.d("REPO_SPLACE","error al agregar item ${exception.message}")
                        msgSave = "ERROR AL GUARDAR"
                    }

                asyncProcess.await()

            }
        }catch (e: Exception){
            Log.d("REPO_SPLACE","error ${e.localizedMessage}")
        }

        return msgSave
    }
}