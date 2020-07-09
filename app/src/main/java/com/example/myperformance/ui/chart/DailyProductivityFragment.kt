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
import com.androidplot.xy.XYPlot
import com.example.myperformance.R
import com.example.myperformance.model.TimePerform
import com.example.myperformance.viewModel.TimePerformViewModel
import com.example.myperformance.workCharts.ChartDataHolder
import com.example.myperformance.workCharts.CriterionChart
import com.example.myperformance.workCharts.GraphicPainter
import kotlinx.android.synthetic.main.daily_productivity.*
import java.lang.Exception

class DailyProductivityFragment() : Fragment() {

    lateinit var criteria: CriterionChart

    val rDataChart: ChartDataHolder<Any>

    init {
        rDataChart = ChartDataHolder()
    }
    private lateinit var viewModel: TimePerformViewModel


    constructor(criterionChart: CriterionChart) : this() {
        criteria = criterionChart
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.daily_productivity,container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimePerformViewModel::class.java)
        viewModel.allTimePerform.observeForever {
            if(it.size != 0){
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    context?.let { it1 -> showError(it1) }
                }
            }
        }
    }

    private fun loadData(list: List<TimePerform>){
        rDataChart.setList(list)
        val keyDate: List<Number> = rDataChart.listDayOfWeek as List<Number>
        val valueTime = rDataChart.listTimeValue
    }

    private fun showError(context: Context){
        Log.d("MyLog", "Error message")
        Toast.makeText(context, R.string.error_loading_data, Toast.LENGTH_LONG).show()
    }





}