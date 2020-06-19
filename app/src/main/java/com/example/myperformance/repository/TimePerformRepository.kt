package com.example.myperformance.repository

import androidx.lifecycle.LiveData

import com.example.myperformance.dao.TimePerformDao
import com.example.myperformance.model.TimePerform


class TimePerformRepository(private val timePerformDao: TimePerformDao) {
    val allValueTimePerform: LiveData<List<TimePerform>> =
            timePerformDao.getAllDataAboutTime()

    suspend fun insert(timePerform: TimePerform){
        timePerformDao.insert(timePerform)
    }

    suspend fun deleteAll(){
        timePerformDao.deleteAll()
    }
}