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
            viewState.showTime(timeValue = timeValue.toString())
        } else
            viewState.showError()
    }

    fun timerListen() : BroadcastReceiver{
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
}