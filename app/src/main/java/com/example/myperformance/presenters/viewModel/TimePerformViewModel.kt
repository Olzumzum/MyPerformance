package com.example.myperformance.presenters.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.myperformance.app.App
import com.example.myperformance.model.CriterionChart
import com.example.myperformance.model.TimePerform
import com.example.myperformance.repository.TimePerformRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TimePerformViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimePerformRepository
    lateinit var allTimePerform: LiveData<List<TimePerform>>
    lateinit var criterionChart: CriterionChart

    init {
        repository = (getApplication() as App).appComponent().timePerformRepository()
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
            CriterionChart.WEEK -> repository.getDataByPeriod()
        }
    }


}