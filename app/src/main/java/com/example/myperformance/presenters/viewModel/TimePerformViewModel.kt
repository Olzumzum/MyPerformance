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
    private val repository: TimePerformRepository
    lateinit var allTimePerform: LiveData<List<TimePerform>>
    var criterionChart: CriterionChart? = null
    var todayList: LiveData<List<TimePerform>>? = null

    init {
        repository = (getApplication() as App).appComponent().timePerformRepository()
    }

    //ЧТО СО СКОУПОМ???????????????
    fun initialization() {
        allTimePerform = getDataByPeriod()
    }

    fun insert(timePerform: TimePerform) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timePerform)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun getDataToday(){
        todayList = repository.getDataToday()
    }



    private fun getDataByPeriod(): LiveData<List<TimePerform>> {
        return when (criterionChart) {
            CriterionChart.ALL -> repository.getAllData()
            CriterionChart.TODAY -> repository.getDataToday()
            CriterionChart.WEEK -> repository.getDataByPeriod()
            else -> throw NullPointerException("CriterionChart hasn't been initialized")
        }
    }


}