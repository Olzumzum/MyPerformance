package com.example.myperformance.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myperformance.data.model.TimePerform


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

    @Query("SELECT * FROM timeperform_table where date_perf BETWEEN :start and :finish")
     fun getDataByPeriod(start: Long, finish: Long): LiveData<List<TimePerform>>


}