package com.example.myperformance.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface DailyProductivityView: MvpView {
    fun showError(idResource: Int)
    fun showData(keyDate: List<Number>, valueTime: List<Number>)
    fun loadData()
    fun endLoading()
}