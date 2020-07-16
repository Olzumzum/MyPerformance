package com.example.myperformance.presenters

import com.example.myperformance.ui.timer.TimerView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class TimerPresenter: MvpPresenter<TimerView>() {

    fun saveTime(time: Long){
        if(time < 0)
            viewState.showError()

    }
}