package com.emiranda.myapplication.myProfile.uploadImage.model

import android.net.Uri
import com.emiranda.myapplication.myProfile.uploadImage.mObject.PhotoObject
import com.emiranda.myapplication.util.MResource

interface UploadImageDataSource {

    suspend fun uploadImage(photo: PhotoObject) : MResource<MutableList<String>>
}