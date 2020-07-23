package com.example.myperformance.presenters

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.myperformance.R
import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.data.model.TimePerform
import com.example.myperformance.presenters.viewModel.TimePerformViewModel
import com.example.myperformance.view.DailyProductivityView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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


    private fun setFormat() {
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

    fun observe() {
        when (criterionChart) {
            CriterionChart.TODAY -> {
                observeAllData()
            }
            CriterionChart.WEEK -> {
                observeAllData()

            }
            CriterionChart.ALL -> {
                observeAllData()
            }
        }
    }


    private fun observeAllData() {
        load(viewModel.allTimePerform)
    }

    private fun observeWeekData() {
        load(viewModel.weekTimePerform)
    }

    private fun observeTodayData() {
        load(viewModel.todayTimePerform)

    }

    private fun load(liveData: LiveData<List<TimePerform>>) {

        liveData.observeForever {
            if (it.isNotEmpty()) {
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    viewState.showError(idResource = R.string.error_loading_data)
                }
            } else {
                viewState.loadData()
                viewState.showError(idResource = R.string.error_empty_data_chart)
            }
        }
    }


    private fun <E> loadData(list: List<E>) {
        Log.e("MyLog", "Начало форматирования")
        setFormat()
        rDataChart.setList(list)
        val keyDate: List<Number> = rDataChart.listDayOfWeek as List<Number>
        val valueTime: List<Number> = rDataChart.listTimeValue as List<Number>
        Log.e("MyLog", "отправка")
        viewState.showData(keyDate, valueTime)
    }
}