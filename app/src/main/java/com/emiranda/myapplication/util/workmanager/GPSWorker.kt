package com.emiranda.myapplication.util.workmanager

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.emiranda.myapplication.util.Constants
import com.emiranda.myapplication.util.Constants.Companion.EMAIL_KEY_PREFS
import com.emiranda.myapplication.util.Constants.Companion.TAG_WORKGPS
import com.emiranda.myapplication.util.PreferencesUtil
import com.emiranda.myapplication.util.callback.MyLocationCallback
import com.emiranda.myapplication.util.workmanager.mObject.UserApp
import com.emiranda.myapplication.util.workmanager.model.GPSWorkerDataSource
import com.google.android.gms.location.*
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class GPSWorker(val context: Context, parameters: WorkerParameters) : CoroutineWorker(context,parameters), KoinComponent{

    private val repo by inject<GPSWorkerDataSource>()
    private val prefs : PreferencesUtil by inject()
    private var name=""
    private var email=""
    private var lat : Double? = 0.0
    private var lng : Double? = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val WORKER_ID = "GPSSaveWorkerID"
    }

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {

        Log.d(TAG_WORKGPS, "doWork: Done");
        Log.d(TAG_WORKGPS, "onStartJob: STARTING JOB..")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val asyncProccess = fusedLocationClient.lastLocation.
            addOnSuccessListener { location: Location? ->
                lat = location?.latitude
                lng = location?.longitude
            }
        asyncProccess.await()

        email = prefs.getValueString(EMAIL_KEY_PREFS).toString()
        name = prefs.getValueString(Constants.NAME_KEY_PREFS).toString()
        val currentDate: String = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        val user = UserApp(email,name,lat,lng,currentDate)
        repo.saveUserLocation(user)

        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun captureLastLocation(callback: MyLocationCallback){

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.
        addOnSuccessListener { location: Location? ->
            email = prefs.getValueString(EMAIL_KEY_PREFS).toString()
            name = prefs.getValueString(Constants.NAME_KEY_PREFS).toString()
            var lat = location?.latitude
            var lng = location?.longitude
            callback.awaitLatLng(lat,lng)
        }

    }
}

