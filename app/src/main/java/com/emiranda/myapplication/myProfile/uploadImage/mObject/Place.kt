package com.emiranda.myapplication.myProfile.uploadImage.mObject

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class Place(
    @PrimaryKey val id : String = "",
    @ColumnInfo(name = "detail_place")
    val detail_place: String = "",
    @ColumnInfo(name = "images")
    val images : MutableList<String> = mutableListOf(),
    @ColumnInfo(name = "createdAt")
    val createdAt : String = "")