package com.example.myperformance.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myperformance.model.TimePerforme

/**
 * Dao to get performance table data
 */
@Dao
interface TimePerformeDao {
    @Query("SELECT * FROM timeperforme_table")
    fun getAllDataAboutTime(): LiveData<List<TimePerforme>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timePerforme: TimePerforme)

    @Query("DELETE FROME timeperforme_table")
    suspend fun deleteAll()
}