package com.example.myperformance.repository

import androidx.lifecycle.LiveData
import com.example.myperformance.dao.TimePerformeDao
import com.example.myperformance.model.TimePerforme

class TimePerformeRepository(private val timePerformeDao: TimePerformeDao) {
    val allValueTimePerforme: LiveData<List<TimePerforme>> = timePerformeDao.getAllDataAboutTime()

    suspend fun insert(timePerforme: TimePerforme){
        timePerformeDao.insert(timePerforme)
    }
}