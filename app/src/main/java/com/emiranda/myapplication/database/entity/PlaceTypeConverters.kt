package com.emiranda.myapplication.database.entity

import androidx.room.TypeConverter
import com.google.gson.Gson

class PlaceTypeConverters {

    @TypeConverter
    fun listToJson(urlPhoto: MutableList<String>) = Gson().toJson(urlPhoto)

    @TypeConverter
    fun jsonToList(urlPhoto : String) = Gson().fromJson(urlPhoto, Array<String>::class.java).toList()

}