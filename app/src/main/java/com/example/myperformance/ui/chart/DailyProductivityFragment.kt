package com.example.myperformance.ui.chart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myperformance.R
import com.example.myperformance.presenters.viewModel.TimePerformViewModel

import com.example.myperformance.model.CriterionChart
import com.example.myperformance.presenters.ChartDataHolder
import com.example.myperformance.presenters.ReturningDataChart

import kotlinx.android.synthetic.main.daily_productivity.*
import java.lang.Exception

class DailyProductivityFragment(val criterionChart: CriterionChart) : Fragment() {

    private val rDataChart: ReturningDataChart<Any>
    private lateinit var viewModel: TimePerformViewModel
    private val dateFromat: String

    init {
        rDataChart = ChartDataHolder<Any>()
        when (criterionChart) {
            CriterionChart.TODAY -> dateFromat = "h:mm a"
            else -> dateFromat = "dd.MM"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.daily_productivity, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TimePerformViewModel::class.java)
        viewModel.criterionChart = criterionChart
        viewModel.initialization()

        viewModel.allTimePerform.observeForever {
            if (it.size != 0) {
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    context?.let { it1 -> showError(it1) }
                }
            }
        }
    }

    private fun <E> loadData(list: List<E>) {
//        list as List<TimePerform>
//        Log.e("MyLog", "criteria $criterionChart")
//        list.forEach {
//            val date = GregorianCalendar()
//            date.timeInMillis = it.datePerform
//
//            Log.e("MyLog", "value ${date.time} ${it.timePerf}")
//        }
        rDataChart.setList(list)
        val keyDate: List<Number> = rDataChart.listDayOfWeek as List<Number>
        val valueTime: List<Number> = rDataChart.listTimeValue as List<Number>
        val painer = GraphicPainter()
        painer.setFormat(dateFromat)
        painer.paint(plot, keyDate, valueTime)
    }

    private fun showError(context: Context) {
        Log.d("MyLog", "Error message")
        Toast.makeText(context, R.string.error_loading_data, Toast.LENGTH_LONG).show()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}