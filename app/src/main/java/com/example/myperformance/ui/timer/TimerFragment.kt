package com.example.myperformance.ui.timer

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myperformance.R
import com.example.myperformance.presenters.TimerPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class TimerFragment : MvpAppCompatFragment(), View.OnClickListener, TimerView {
    private val FLAG_TIMER_COUNT = "TimerCount"
    private val FLAG_RUNNING_TIMER = "RunningTimer"

    private var viewRoot: View? = null

    @InjectPresenter
    lateinit var timerPresenter: TimerPresenter


    //provides a time
    lateinit var coutingTimeView: CoutingTimeView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val orientation = activity?.resources?.configuration?.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            viewRoot = inflater.inflate(R.layout.fragment_timer, container, false)
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            viewRoot = inflater.inflate(R.layout.fragment_timer_horizontal, container, false)


        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val startCoutingTime = viewRoot?.findViewById<Button>(R.id.start_countring_time)
        val pauseCoutingTime = viewRoot?.findViewById<Button>(R.id.pause_countring_time)
        val stopCoutingTime = viewRoot?.findViewById<Button>(R.id.stop_countring_time)


        val chronometerEmployment = viewRoot?.findViewById<Chronometer>(R.id.chronometer_employment)

        coutingTimeView = CoutingTimeView(chronometerEmployment)

        //restore state
        //get time counter value
        savedInstanceState.let {
            val pauseOffset = savedInstanceState?.getLong(FLAG_TIMER_COUNT)
            val running = savedInstanceState?.getBoolean(FLAG_RUNNING_TIMER)
            savedInstanceState?.putLong(FLAG_TIMER_COUNT, 0)
            if (pauseOffset != null) {
                coutingTimeView.pauseOffset = pauseOffset
            }

            if (running != null) {
                coutingTimeView.running = running
            }

            coutingTimeView.restartChronometr()
        }

        startCoutingTime?.setOnClickListener(this)
        pauseCoutingTime?.setOnClickListener(this)
        stopCoutingTime?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_countring_time -> {
                coutingTimeView.startCount()

            }
            R.id.pause_countring_time -> {

                coutingTimeView.pauseCounting()
            }
            R.id.stop_countring_time -> {
                coutingTimeView.stopCounting()

            }

        }


    }

    override fun onPause() {
        super.onPause()
        //set the time elapsed since the start of the timer
        coutingTimeView.setCurrentTime()
        //change flag - timer execution is paused to restart
        coutingTimeView.running = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(FLAG_TIMER_COUNT, coutingTimeView.pauseOffset)
        outState.putBoolean(FLAG_RUNNING_TIMER, coutingTimeView.running)
        super.onSaveInstanceState(outState)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error saving data", Toast.LENGTH_SHORT).show()
    }


}
