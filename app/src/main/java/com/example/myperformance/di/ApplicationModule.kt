package com.example.myperformance.di

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.myperformance.database.TimePerformDao
import com.example.myperformance.database.TimePerformRoomDatabase
import com.example.myperformance.ui.timer.CoutingTimeView
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {

//    @Provides
//    fun providesApplication(): Application = application
//
//    @Provides
//    fun provideTimePerformDao(): TimePerformDao {
//        return TimePerformRoomDatabase.getDatabase(application, viewModelScope)
//                .timePerformDao()
//    }
}