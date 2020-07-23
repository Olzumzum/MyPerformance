package com.example.myperformance.presenters.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.myperformance.app.App
import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.data.model.TimePerform
import com.example.myperformance.data.repository.TimePerformRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException


class TimePerformViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimePerformRepository = (getApplication() as App).appComponent().timePerformRepository()

    var allTimePerform: LiveData<List<TimePerform>>
    var weekTimePerform: LiveData<List<TimePerform>>
    var todayTimePerform: LiveData<List<TimePerform>>

    var criterionChart: CriterionChart? = null


    init {
        allTimePerform = repository.getAllData()
        weekTimePerform = repository.getDataByPeriod()
        todayTimePerform = repository.getDataToday()
    }

    fun insert(timePerform: TimePerform) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timePerform)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }



    private fun getDataByPeriod(): LiveData<List<TimePerform>> {
        return when (criterionChart) {
            CriterionChart.ALL -> allTimePerform

            CriterionChart.TODAY -> todayTimePerform

            CriterionChart.WEEK -> weekTimePerform

            else -> throw NullPointerException("CriterionChart hasn't been initialized")
        }
    }


}