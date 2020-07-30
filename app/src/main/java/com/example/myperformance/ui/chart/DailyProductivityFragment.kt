package com.example.myperformance.ui.chart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myperformance.R
import com.example.myperformance.presenters.viewModel.TimePerformViewModel

import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.data.model.TimePerform
import com.example.myperformance.presenters.ChartDataHolder
import com.example.myperformance.presenters.ReturningDataChart
import com.example.myperformance.view.DailyProductivityView

import kotlinx.android.synthetic.main.daily_productivity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.NullPointerException

/**
 *fragment display plotting performance data
 */
class DailyProductivityFragment : Fragment(), DailyProductivityView {

    lateinit var progressBarLoading: ProgressBar

    lateinit var criterionChart: CriterionChart
    private val rDataChart: ReturningDataChart<Any> = ChartDataHolder()

    // initialized depending on the type of graph, indicates the format
    // of data output along the x axis
    lateinit var dateFormat: String

    lateinit var viewModel: TimePerformViewModel
    var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimePerformViewModel::class.java)
        position = arguments?.getInt(ARG_SECTION_NUMBER)
        Log.e("MyLog", "position $position")

    }

    fun criterion(position: Int?) {
        when (position) {
            0 -> criterionChart = CriterionChart.TODAY
            1 -> criterionChart = CriterionChart.WEEK
            2 -> criterionChart = CriterionChart.ALL
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.daily_productivity, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBarLoading = view?.findViewById(R.id.progressBar_loading_chart)!!
        criterion(position)
        loadData()
        observe()

    }

    /**
     * specifies the format of the x-axis data
     */
    private fun setFormat() {
        Log.e("MyLog", "$criterionChart")
        criterionChart.let {
            when (it) {
                CriterionChart.TODAY -> {
                    dateFormat = "dd.MM"

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

    private fun observe() {
        when (criterionChart) {
            CriterionChart.TODAY -> {
                load(viewModel.todayTimePerform)
            }
            CriterionChart.WEEK -> {
                load(viewModel.weekTimePerform)

            }
            CriterionChart.ALL -> {
                load(viewModel.allTimePerform)
            }

        }
    }

    private fun load(liveData: LiveData<List<TimePerform>>) {
        loadData()
        liveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                try {
                    loadData(it)
                } catch (ex: Exception) {
                    showError(idResource = R.string.error_loading_data)
                }
            } else {

                endLoading()
                showError(idResource = R.string.error_empty_data_chart)
            }

        })

    }


    override fun showError(idResource: Int) {
        Log.d("MyLog", "Error message")
        Toast.makeText(context, getString(idResource), Toast.LENGTH_LONG).show()
    }

    /**
     * draws graphs
     */
    override fun showData(keyDate: List<Number>, valueTime: List<Number>) {
        val painter = GraphicPainter()
        painter.setFormat(dateFormat)
        painter.paint(plot, keyDate, valueTime)

        endLoading()
    }

    /**
     *formats data for display
     */
    private fun <E> loadData(list: List<E>) {
        setFormat()
        rDataChart.setList(list)
        val keyDate: List<Number> = rDataChart.listDayOfWeek as List<Number>
        val valueTime: List<Number> = rDataChart.listTimeValue as List<Number>
        showData(keyDate, valueTime)
    }

    override fun loadData() {
        plot.visibility = View.GONE
        progressBarLoading.visibility = View.VISIBLE

    }

    override fun endLoading() {
        progressBarLoading.visibility = View.GONE
        plot.visibility = View.VISIBLE
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(position: Int): DailyProductivityFragment {
            return DailyProductivityFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, position)
                }
            }
        }
    }

}