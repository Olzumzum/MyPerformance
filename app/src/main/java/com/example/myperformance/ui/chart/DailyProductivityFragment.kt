package com.example.myperformance.ui.chart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myperformance.R
import com.example.myperformance.presenters.viewModel.TimePerformViewModel

import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.presenters.DailyProductivityPresenter
import com.example.myperformance.view.DailyProductivityView

import kotlinx.android.synthetic.main.daily_productivity.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

/**
 *fragment display plotting performance data
 */
class DailyProductivityFragment(val criterionChart: CriterionChart) : MvpAppCompatFragment(), DailyProductivityView {

    @InjectPresenter
    lateinit var presenter: DailyProductivityPresenter

    lateinit var progressBarLoading: ProgressBar



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.daily_productivity, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBarLoading = view?.findViewById(R.id.progressBar_loading_chart)!!

        presenter.viewModel = ViewModelProvider(this).get(TimePerformViewModel::class.java)
        presenter.criterionChart = criterionChart
        presenter.observe()
    }



    override fun showError(idResource: Int) {
        Log.d("MyLog", "Error message")
        Toast.makeText(context, getString(idResource), Toast.LENGTH_LONG).show()
    }

    override fun showData(keyDate: List<Number>, valueTime: List<Number>) {
        Log.e("MyLog", "Получение")
        runBlocking {
          val j = launch {
              val painter: GraphicPainter = GraphicPainter()
              painter.setFormat(presenter.dateFormat)
              painter.paint(plot, keyDate, valueTime)
          }
            j.join()
        }
        endLoading()
        Log.e("MyLog", "Конец функции")

    }



    override fun loadData() {
        plot.visibility = View.GONE
        progressBarLoading.visibility = View.VISIBLE

    }

    override fun endLoading() {
        progressBarLoading.visibility = View.GONE
        plot.visibility = View.VISIBLE
    }


}