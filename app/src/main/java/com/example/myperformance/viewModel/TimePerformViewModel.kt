package com.example.myperformance.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.myperformance.app.App
import com.example.myperformance.database.TimePerformDao
import com.example.myperformance.database.TimePerformRoomDatabase
import com.example.myperformance.model.CriterionChart
import com.example.myperformance.model.TimePerform
import com.example.myperformance.repository.TimePerformRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class TimePerformViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimePerformRepository
    lateinit var allTimePerform: LiveData<List<TimePerform>>
    lateinit var criterionChart: CriterionChart

    init {
        repository = (application as App).appComponent().getTimePerformRepository()
    }

    fun initialization() {
        allTimePerform = getDataByPeriod()
    }

    fun insert(timePerform: TimePerform) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timePerform)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }


    private fun getDataByPeriod(): LiveData<List<TimePerform>> {
        return when (criterionChart) {
            CriterionChart.ALL -> repository.getAllData()
            CriterionChart.TODAY -> repository.getDataToday()
            CriterionChart.WEEK -> repository.getDataPeriod()
        }
    }


}