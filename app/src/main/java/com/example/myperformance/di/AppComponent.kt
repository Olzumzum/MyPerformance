package com.example.myperformance.di

import com.example.myperformance.data.repository.TimePerformRepository
import com.example.myperformance.ui.chart.DailyProductivityFragment
import com.example.myperformance.ui.timer.TimerFragment
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface AppComponent {
    fun inject(fragment: TimerFragment)
    fun inject(fragment: DailyProductivityFragment)

    fun timePerformRepository(): TimePerformRepository


}