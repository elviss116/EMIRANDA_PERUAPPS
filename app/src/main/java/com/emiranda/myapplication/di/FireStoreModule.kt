package com.emiranda.myapplication.di

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module


val providerFireStoreModule = module {

    fun providerFireStoreInstance(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    single { providerFireStoreInstance() }
}