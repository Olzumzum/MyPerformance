package com.example.myperformance.repository

import android.util.Log
import androidx.lifecycle.LiveData

import com.example.myperformance.database.TimePerformDao
import com.example.myperformance.model.CriterionChart
import com.example.myperformance.model.TimePerform
import java.util.*
import javax.inject.Inject


class TimePerformRepository(private val timePerformDao: TimePerformDao) {

    lateinit var criterionChart: CriterionChart
    val allTimePerform = timePerformDao.getAllDataAboutTime()

    suspend fun insert(timePerform: TimePerform) {
        timePerformDao.insert(timePerform)
    }

    suspend fun deleteAll() {
        timePerformDao.deleteAll()
    }

    fun getAllData() = timePerformDao.getAllDataAboutTime()

    fun getDataByPeriod(): LiveData<List<TimePerform>> {
        val start = GregorianCalendar(2020, 5, 10).timeInMillis
        val finish = GregorianCalendar(2020, 7, 20).timeInMillis
        return timePerformDao.getDataByPeriod(start, finish)
    }

    fun getDataByOneDay(date: Long): LiveData<List<TimePerform>> {
        val tomorrow = getListOneDay()
        return timePerformDao.getDataByOneDay(date, tomorrow)
    }

    fun getDataToday(): LiveData<List<TimePerform>> {
        val tommorow = getListOneDay()

        return timePerformDao.getDataByOneDay(today = GregorianCalendar().timeInMillis, tomorrow = tommorow)
    }

    fun getListOneDay(): Long {
        val tomorrow = GregorianCalendar()
        tomorrow.add(Calendar.DATE, 1)
        return tomorrow.timeInMillis
    }
}




