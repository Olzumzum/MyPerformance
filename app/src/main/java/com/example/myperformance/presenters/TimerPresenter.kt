package com.example.myperformance.presenters

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.myperformance.app.App
import com.example.myperformance.presenters.viewModel.TimePerformViewModel
import com.example.myperformance.repository.TimePerformRepository
import com.example.myperformance.ui.timer.TimerFragment
import com.example.myperformance.view.TimerView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class TimerPresenter() : MvpPresenter<TimerView>() {
    private val TAG = "MyLog"
    var application: Application? = null
    private lateinit var viewModel: TimePerformViewModel

    //repository
    private lateinit var repository: TimePerformRepository

    fun saveTime(time: Long) {
        application.let {
            repository = (application as App).appComponent().timePerformRepository()
            if (time < 0)
                viewState.showError()
            else {
                viewState.saveData()
            val countDate = GregorianCalendar()
            Log.e(TAG, "time value = $time and date = ${countDate.time}")
            val timePerformList = repository.getDataToday()
//                viewModel = ViewModelProvider(application.applicationContext).get(TimePerformViewModel::class.java)
                //check
                //logic
//            repository.insert()
            }
        }
        viewState.showButton()
    }
}