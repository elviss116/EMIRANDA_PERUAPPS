package com.emiranda.myapplication.di

import androidx.work.WorkManager
import com.emiranda.myapplication.util.workmanager.GPSWorker
import com.emiranda.myapplication.util.workmanager.WorkManagerHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var providerWorkermodule= module {

    single { WorkManager.getInstance( get() ) }
    single { WorkManagerHelper( get() ) }
    single { GPSWorker(androidContext(), get()) }

}