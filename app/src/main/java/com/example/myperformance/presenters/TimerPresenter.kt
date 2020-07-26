package com.example.myperformance.presenters

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myperformance.app.App
import com.example.myperformance.data.model.TimePerform
import com.example.myperformance.presenters.viewModel.TimePerformViewModel
import com.example.myperformance.data.repository.TimePerformRepository
import com.example.myperformance.view.TimerView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@InjectViewState
class TimerPresenter() : MvpPresenter<TimerView>() {
    private val TAG = "MyLog"
    lateinit var application: Application
    lateinit var viewModel: TimePerformViewModel

    private val TIME_VALUE_EXTRA = "TimerValue"
    private val FINISH_TIME_VALUE = "finishTimeValue"


    //repository
    private lateinit var repository: TimePerformRepository

    fun saveTime(time: Long) {
        application.let {
            repository = (application as App).appComponent().timePerformRepository()
            if (time < 0)
                viewState.showError()
            else {
                viewState.saveData()

                val date = GregorianCalendar()
                val timePerform = TimePerform(date.timeInMillis, time.toInt())

                viewModel.insert(timePerform)
                Log.e(TAG, "Success value ($time) added")
            }
        }
        viewState.showButton()
    }

    fun showTime(timeValue: Long?) {
        if (timeValue != null) {
            val timeValueString = timeFormater(timeValue)

            viewState.showTime(timeValue = timeValueString)
        } else
            viewState.showError()
    }

    fun timerListen(): BroadcastReceiver {
        var finishTimevalue: Long? = 0
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val timeValue = intent?.getLongExtra(TIME_VALUE_EXTRA, 0)
                showTime(timeValue)

                finishTimevalue = intent?.getLongExtra(FINISH_TIME_VALUE, 0)
                finishTimevalue?.let {
                    if (it.compareTo(0) != 0) {
                        saveTime(it)
                        finishTimevalue = 0
                    }
                }
            }

        }
    }

    private fun timeFormater(timeValue: Long): String {

        val time = GregorianCalendar()
        time.set(Calendar.HOUR, 0)
        time.set(Calendar.MINUTE, 0)
        time.set(Calendar.SECOND, timeValue.toInt())
        Log.e("MyLog", "time value ${time.time}")

        val hour = time.get(Calendar.HOUR)
        val minute = time.get(Calendar.MINUTE)
        val second = time.get(Calendar.SECOND)

        var timeValueString = ""

        if (hour == 0)
            timeValueString += "00"
        else
            if (hour < 10)
                timeValueString += "0$hour"
            else timeValueString += "$hour"

        if (minute == 0)
            timeValueString += ":00"
        else
            if (minute < 10)
                timeValueString += ":0$minute"
            else timeValueString += ":$minute"

        if (second == 0)
            timeValueString += ":00"
        else
            if (second < 10)
                timeValueString += ":0$second"
            else timeValueString += ":$second"

        return timeValueString
    }
}