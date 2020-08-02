package com.example.myperformance.view

interface StopwatchView {
    fun showError(idResource: Int)
    fun startStopwatch()
    fun stopStopWatch()
    fun showTime()
}