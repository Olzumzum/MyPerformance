package com.example.myperformance.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface DailyProductivityView{
    fun showError(idResource: Int)
    fun showData(keyDate: List<Number>, valueTime: List<Number>)
    fun loadData()
    fun endLoading()
}