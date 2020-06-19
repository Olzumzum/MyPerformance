package com.example.myperformance.viewModel

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.myperformance.database.TimePerformeRoomDatabase
import com.example.myperformance.model.TimePerforme
import com.example.myperformance.repository.TimePerformeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.time.TimedValue


class TimePerformeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimePerformeRepository
    var allTimePerforme: LiveData<List<TimePerforme>>


    init {
        val timePerformeDao = TimePerformeRoomDatabase.getDatabase(application, viewModelScope).timePerformeDao()
        repository = TimePerformeRepository(timePerformeDao)
        allTimePerforme = repository.allValueTimePerforme

    }

    fun insert(timePerforme: TimePerforme) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timePerforme)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }


}