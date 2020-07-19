package com.example.myperformance.presenters

import android.util.Log
import com.example.myperformance.repository.TimePerformRepository
import com.example.myperformance.ui.timer.TimerView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.sql.Time
import java.util.*

@InjectViewState
class TimerPresenter: MvpPresenter<TimerView>() {
    private val TAG = "MyLog"


    fun saveTime(time: Long){
        if(time < 0)
            viewState.showError()

        val countDate = GregorianCalendar()
        Log.e(TAG, "time value = $time and date = ${countDate.time}")

    }
}