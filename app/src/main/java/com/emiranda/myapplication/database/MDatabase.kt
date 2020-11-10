package com.emiranda.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emiranda.myapplication.database.dao.PLACE_DAO
import com.emiranda.myapplication.database.entity.PlaceTypeConverters
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place


@Database(entities = [Place::class] , version = 1 , exportSchema = false)
@TypeConverters(PlaceTypeConverters::class)
abstract class MDatabase : RoomDatabase(){
    abstract val placeDao : PLACE_DAO
}