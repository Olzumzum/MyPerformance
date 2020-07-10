package com.example.myperformance.repository

import androidx.lifecycle.LiveData

import com.example.myperformance.database.TimePerformDao
import com.example.myperformance.model.CriterionChart
import com.example.myperformance.model.TimePerform


class TimePerformRepository(private val timePerformDao: TimePerformDao) {

    lateinit var criterionChart: CriterionChart
    val allTimePerform = timePerformDao.getAllDataAboutTime()

    suspend fun insert(timePerform: TimePerform){
        timePerformDao.insert(timePerform)
    }

    suspend fun deleteAll(){
        timePerformDao.deleteAll()
    }

    fun getAllData() = timePerformDao.getAllDataAboutTime()



}