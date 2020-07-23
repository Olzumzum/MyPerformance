package com.example.myperformance.presenters

import android.app.Application
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
    private lateinit var viewModel: TimePerformViewModel
    lateinit var fragmentOwner: Fragment

    //repository
    private lateinit var repository: TimePerformRepository

    fun saveTime(time: Long){
        application.let {
            repository = (application as App).appComponent().timePerformRepository()
            if (time < 0)
                viewState.showError()
            else {
                viewState.saveData()

                val date = GregorianCalendar()
                val timePerform = TimePerform(date.timeInMillis, time.toInt())

                viewModel = ViewModelProvider(fragmentOwner).get(TimePerformViewModel::class.java)
                viewModel.insert(timePerform)
            }
        }
        viewState.showButton()
    }
}