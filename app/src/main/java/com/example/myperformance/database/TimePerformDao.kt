package com.example.myperformance.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myperformance.model.TimePerform


/**
 * Dao to get performance table data
 */
@Dao
interface TimePerformDao{
    @Query("SELECT * FROM timeperform_table")
    fun getAllDataAboutTime(): LiveData<List<TimePerform>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timePerform: TimePerform)

    @Query("DELETE FROM timeperform_table")
    suspend fun deleteAll()
}