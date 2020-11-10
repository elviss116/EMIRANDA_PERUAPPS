package com.emiranda.myapplication.util.workmanager.viewmodel

import androidx.lifecycle.ViewModel
import com.emiranda.myapplication.util.workmanager.WorkManagerHelper
import com.emiranda.myapplication.util.workmanager.model.GPSWorkerDataSource

class GPSWorkerViewModel(private val helper: WorkManagerHelper) : ViewModel() {

    fun setupWorker(){
        helper.setupSynchronization()
    }

    fun stopWorker(){
        helper.stopSynchronization()
    }
}