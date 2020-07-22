package com.example.myperformance.di

import android.app.Application
import com.example.myperformance.database.TimePerformDao
import com.example.myperformance.database.TimePerformRoomDatabase
import com.example.myperformance.repository.TimePerformRepository
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun providesApplication(): Application = application


    @Provides
    fun provideTimePerformDao(application: Application): TimePerformDao {
        return  TimePerformRoomDatabase.getDatabase(application)
                .timePerformDao()
    }


    @Provides
    fun provideTimePerformRepository(timePerformeDao: TimePerformDao): TimePerformRepository =
            TimePerformRepository(timePerformDao = timePerformeDao)
}