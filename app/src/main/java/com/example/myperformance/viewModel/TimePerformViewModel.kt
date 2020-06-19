package com.example.myperformance.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.myperformance.database.TimePerformRoomDatabase
import com.example.myperformance.model.TimePerform
import com.example.myperformance.repository.TimePerformRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TimePerformViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimePerformRepository
    var allTimePerform: LiveData<List<TimePerform>>


    init {
        val timePerformDao = TimePerformRoomDatabase.getDatabase(application, viewModelScope)
                .timePerformDao()
        repository = TimePerformRepository(timePerformDao)
        allTimePerform = repository.allValueTimePerform

    }

    fun insert(timePerform: TimePerform) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timePerform)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }


}