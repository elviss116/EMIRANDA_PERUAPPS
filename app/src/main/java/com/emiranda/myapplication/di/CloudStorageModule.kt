package com.emiranda.myapplication.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module


val providerFirebaseStorageInstanceModule = module {

    fun providerFirebaseStorage() : StorageReference{
        return Firebase.storage.reference
    }

    single { providerFirebaseStorage() }
}