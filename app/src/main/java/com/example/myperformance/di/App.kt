package com.example.myperformance.di

import android.app.Application
import com.example.myperformance.ui.ScrolbarActivity
import com.example.myperformance.ui.timer.TimerFragment
import dagger.Component
import dagger.internal.DaggerCollections

class App: Application() {
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = createComponent()
    }

    fun applicationComponent() = applicationComponent

    fun createComponent(): ApplicationComponent =
            DaggerApplicationComponent.builder().build()
}