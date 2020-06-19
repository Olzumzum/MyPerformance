package com.example.myperformance.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myperformance.model.TimePerforme


///**
// * Dao to get performance table data
// */
@Dao
interface TimePerformeDao{
    @Query("SELECT * FROM timeperforme_table")
    fun getAllDataAboutTime(): LiveData<List<TimePerforme>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timePerforme: TimePerforme)

    @Query("DELETE FROM timeperforme_table")
    suspend fun deleteAll()
}