package com.emiranda.myapplication.di

import android.app.Application
import androidx.room.Room
import com.emiranda.myapplication.database.MDatabase
import com.emiranda.myapplication.database.dao.PLACE_DAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val providerRoomDatabaseModule = module {

    fun providerDatabase(application: Application) : MDatabase{
        return Room.databaseBuilder(application, MDatabase::class.java,"MYPLACES_DB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun providerPlace_DAO(mDatabase: MDatabase) : PLACE_DAO{
        return mDatabase.placeDao
    }

    single { providerDatabase(androidApplication()) }
    single { providerPlace_DAO( get() ) }

}