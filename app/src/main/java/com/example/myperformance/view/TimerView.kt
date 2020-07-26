package com.example.myperformance.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface TimerView: MvpView {
    fun showError()
    fun saveData()
    fun showButton()
    fun showTime(timeValue: String)
}