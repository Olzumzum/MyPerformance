package com.example.myperformance.ui.stopwatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myperformance.R
import com.example.myperformance.view.StopwatchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import org.w3c.dom.Text

class StopWatchFragment : Fragment(), StopwatchView {

    private var timeValue: Int = 0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val viewRoot = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        val bottomBar = viewRoot?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val nav = findNavController()
        NavigationUI.setupWithNavController(bottomBar!!, nav)


        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        stopwatchButton.setOnClickListener {
            val value = timeValueStopWatch?.text.toString()
            if(value.isNotEmpty()) {
                try {
                    timeValue = value.toInt()
                } catch (e: ClassCastException) {
                    showError(R.string.error_cast_stopwatch_message)
                }

                startStopwatch()
            }
        }
    }

    override fun showError(idResource: Int) {
        Toast.makeText(context, getString(idResource), Toast.LENGTH_LONG).show()
    }

    override fun startStopwatch() {
        timeValueStopWatch?.visibility = View.GONE
        decrease_time_view?.visibility = View.VISIBLE
    }

    override fun stopStopWatch() {
    }

    override fun showTime() {
        TODO("Not yet implemented")
    }


}
