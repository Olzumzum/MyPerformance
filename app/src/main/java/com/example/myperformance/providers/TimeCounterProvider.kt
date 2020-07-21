package com.example.myperformance.providers

import android.app.Application
import com.example.myperformance.app.App
import com.example.myperformance.repository.TimePerformRepository

class TimeCounterProvider(application: Application) {
    private val repository: TimePerformRepository

    init {
        repository = (application as App).appComponent().timePerformRepository()
    }

    fun timeSave(date: Long, timedValue: Int){
//        val changeDate = repository.getDataByOneDay(date)
//        Log.e("MyLog", "date ${changeDate.value}")
    }
}