package com.example.myperformance.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface StopwatchView: MvpView {
    fun showError(idResource: Int)
    fun startStopwatch()
    fun stopStopWatch()
    fun showTime(timeValue: Int)
}