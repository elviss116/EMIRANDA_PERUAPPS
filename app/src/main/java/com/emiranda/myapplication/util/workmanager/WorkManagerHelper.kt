package com.emiranda.myapplication.util.workmanager

import androidx.work.*
import java.util.concurrent.TimeUnit

open class WorkManagerHelper(private val workManager: WorkManager) {

    fun setupSynchronization(){
        val constraints = buildConstraints()
        val worker = buildWorker(constraints)

        workManager.enqueueUniquePeriodicWork(
            GPSWorker.WORKER_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )

    }

    private fun buildConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    private fun buildWorker(constraints: Constraints): PeriodicWorkRequest{
        return PeriodicWorkRequestBuilder<GPSWorker>(
            30,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
    }

    fun stopSynchronization(){
        workManager.cancelUniqueWork(GPSWorker.WORKER_ID)
    }
}