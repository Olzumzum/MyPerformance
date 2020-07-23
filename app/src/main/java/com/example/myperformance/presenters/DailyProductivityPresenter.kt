package com.example.myperformance.presenters

import android.util.Log
import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.presenters.viewModel.TimePerformViewModel
import com.example.myperformance.view.DailyProductivityView
import moxy.InjectViewState
import moxy.MvpPresenter
import java.lang.Exception

@InjectViewState
class DailyProductivityPresenter() : MvpPresenter<DailyProductivityView>() {
    lateinit var viewModel: TimePerformViewModel
    var criterionChart: CriterionChart? = null

    // stores a link to a class that prepares data for plotting
    private val rDataChart: ReturningDataChart<Any> = ChartDataHolder()

    // initialized depending on the type of graph, indicates the format
    // of data output along the x axis
    lateinit var dateFormat: String


    private fun setFormat(){
        criterionChart.let {
            when (it) {
                CriterionChart.TODAY -> {
                    dateFormat = "h:mm a"

                }
                CriterionChart.WEEK -> {
                    dateFormat = "dd.MM"

                }
                CriterionChart.ALL -> {
                    dateFormat = "dd.MM"
                }
            }
        }
    }

    fun observe(){
        when (criterionChart) {
            CriterionChart.TODAY -> {
                observeTodayData()
        }
            CriterionChart.WEEK -> {
            observeWeekData()

        }
            CriterionChart.ALL -> {
            observeAllData()
        }
        }
    }


    private fun observeAllData() {
        viewModel.criterionChart = criterionChart

        viewModel.allTimePerform.observeForever {
            Log.e("MyLog", "Мы грузили")
            if (it.isNotEmpty()) {
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    viewState.showError()
                }
            }
        }
    }

    private fun observeWeekData() {
        viewModel.criterionChart = criterionChart

        viewModel.weekTimePerform.observeForever {
            Log.e("MyLog", "Мы грузили")
            if (it.isNotEmpty()) {
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    viewState.showError()
                }
            }
        }
    }

    private fun observeTodayData(){
        viewModel.criterionChart = criterionChart

        viewModel.todayTimePerform.observeForever {
            Log.e("MyLog", "Мы грузили")
            if (it.isNotEmpty()) {
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    viewState.showError()
                }
            }
        }
    }



    private fun <E> loadData(list: List<E>) {
        setFormat()
        Log.e("MyLog", "Мы были в установке данных")
        rDataChart.setList(list)
        val keyDate: List<Number> = rDataChart.listDayOfWeek as List<Number>
        val valueTime: List<Number> = rDataChart.listTimeValue as List<Number>
        Log.e("MyLog", "данные ${keyDate.size} ${valueTime.size}")
        viewState.showData(keyDate, valueTime)
    }
}