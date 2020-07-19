package com.example.myperformance.di

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.myperformance.database.TimePerformDao
import com.example.myperformance.database.TimePerformRoomDatabase
import com.example.myperformance.presenters.TimerPresenter
import com.example.myperformance.repository.TimePerformRepository
import com.example.myperformance.ui.timer.CoutingTimeView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun providesApplication(): Application = application


    @Provides
    fun provideTimePerformDao(application: Application): TimePerformDao {
        return  TimePerformRoomDatabase.getDatabase(application)
                .timePerformDao()
    }

    @Provides
    fun provideTimePerformRepository(timePerformeDao: TimePerformDao): TimePerformRepository
            = TimePerformRepository(timePerformDao = timePerformeDao)
}