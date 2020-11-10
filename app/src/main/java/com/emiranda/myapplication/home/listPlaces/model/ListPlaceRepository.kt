package com.emiranda.myapplication.home.listPlaces.model

import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import com.emiranda.myapplication.util.MResource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ListPlaceRepository(private val firestore: FirebaseFirestore) : ListPlaceDataSource {

    override suspend fun getPlaces(): Flow<MResource<MutableList<Place>>> = callbackFlow {

        val myPlaces : MutableList<Place> = arrayListOf()
        val places = firestore.collection("places")

        val suscription = places.addSnapshotListener { docSnapshot, firebaseFireStoreException ->

            if (docSnapshot!=null){
                myPlaces.clear()
                for (docu in docSnapshot.documents){
                    val obb = docu.toObject(Place::class.java)
                    myPlaces.add(obb!!)
                }

                offer(MResource.Success(myPlaces))
            }else{
                channel.close(firebaseFireStoreException?.cause)
            }
        }
        awaitClose{suscription.remove()}


        /*
        ######################## NO SE USA #####################
        val suscr = places.get().addOnCompleteListener {

            if (it.isSuccessful){
                for (document in it.result!!){
                    /*
                    val idPlace = document.data.getValue("id").toString()
                    val dPlace = document.data.getValue("detail_place").toString()
                    val datePlace = document.data.getValue("createdAt").toString()
                    val pPhoto = document.data.getValue("images")
                    val photos : MutableList<String> = arrayListOf()

                    val zz : List<String> = document.getString("images")

                    val objectPlace = Place(idPlace,dPlace,photos,datePlace)
                    */
                    val obb = document.toObject(Place::class.java)
                    myPlaces.add(obb)
                }
            }
        }
        suscr.await()
        emit(MResource.Success(myPlaces))*/
    }
}
