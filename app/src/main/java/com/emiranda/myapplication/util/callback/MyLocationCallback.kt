package com.emiranda.myapplication.util.callback

interface MyLocationCallback {
    fun awaitLatLng(lat: Double?, lng: Double?)
}