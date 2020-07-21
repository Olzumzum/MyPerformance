package com.example.myperformance.app

import android.app.Application
import com.example.myperformance.di.AppComponent
import com.example.myperformance.di.ApplicationModule
import com.example.myperformance.di.DaggerAppComponent

class App: Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    fun appComponent() = appComponent

    private fun createAppComponent() {
       appComponent = DaggerAppComponent.builder()
               .applicationModule(ApplicationModule(this))
               .build()

    }
}