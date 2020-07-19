package com.example.myperformance.di

import com.example.myperformance.ui.ScrolbarActivity
import com.example.myperformance.ui.timer.TimerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(fragment: TimerFragment)

}