package com.emiranda.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place

@Dao
interface PLACE_DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlace(places : MutableList<Place>)

    @Query("delete from place")
    suspend fun deleteAllPlace()

    @Query("select * from place")
    fun getAllPlace(): LiveData<MutableList<Place>>

    @Query("select count(*) from place")
    fun getItemsCount(): LiveData<Int>

}