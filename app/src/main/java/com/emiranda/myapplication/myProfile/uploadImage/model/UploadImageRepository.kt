package com.emiranda.myapplication.myProfile.uploadImage.model

import android.net.Uri
import android.util.Log
import com.emiranda.myapplication.myProfile.uploadImage.mObject.PhotoObject
import com.emiranda.myapplication.util.MResource
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File


class UploadImageRepository(private val storage: StorageReference) : UploadImageDataSource{

    override suspend fun uploadImage(photo: PhotoObject): MResource<MutableList<String>> {
        try {
            val urlDownload : MutableList<String> = arrayListOf()
            photo.let { listUri ->

                listUri.uri.forEach { imageUri ->

                    val file = File(imageUri.path)
                    val ref = storage.child("images/${file.name}")
                    var uploadTask = ref.putFile(imageUri)

                    val urlTask = uploadTask.continueWithTask { task ->

                        if (!task.isSuccessful) {
                            task.exception?.let { exception ->
                                Log.d("REPO_URL", exception.localizedMessage)
                            }
                        }
                        ref.downloadUrl

                    }.addOnCompleteListener {
                        val downloadUri = it.result
                        urlDownload.add(downloadUri.toString())
                    }
                    urlTask.await()
                }
                //println(urlDownload)
                return MResource.Success(urlDownload)
            }
        } catch (e: Exception) {
            Log.d("REPO_UPLOAD", e.localizedMessage)
            return MResource.Failure(e)
        }

    }
}