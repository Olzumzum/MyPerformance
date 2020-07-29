package com.example.myperformance.data.repository

import androidx.lifecycle.LiveData

import com.example.myperformance.data.database.TimePerformDao
import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.data.model.TimePerform
import java.util.*


class TimePerformRepository(private val timePerformDao: TimePerformDao) {

    lateinit var criterionChart: CriterionChart
    val allTimePerform = timePerformDao.getAllDataAboutTime()

    suspend fun insert(timePerform: TimePerform) {
        timePerformDao.insert(timePerform)
    }

    suspend fun deleteAll() {
        timePerformDao.deleteAll()
    }

    fun getAllData(): LiveData<List<TimePerform>> = timePerformDao.getAllDataAboutTime()

    fun getDataByPeriod(): LiveData<List<TimePerform>> {
        val start = GregorianCalendar(2020, 5, 10).timeInMillis
        val finish = GregorianCalendar(2020, 7, 20).timeInMillis
        return timePerformDao.getDataByPeriod(start, finish)
    }

    fun getDataToday(): LiveData<List<TimePerform>> {
        val tommorow = getListOneDay()
        val start = GregorianCalendar(2020, 6, 29).timeInMillis
        val finish = GregorianCalendar(2020, 6, 29).timeInMillis
        return timePerformDao.getDataByPeriod(start, finish)
    }

    private fun getListOneDay(): Long {
        val tomorrow = GregorianCalendar()
        tomorrow.add(Calendar.DATE, 1)
        return tomorrow.timeInMillis
    }
}




