package com.example.myperformance.ui.timer

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.example.myperformance.R
import com.example.myperformance.worktime.CoutingTime

class TimerFragment : Fragment(), View.OnClickListener {
    private val FLAG_TIMER_COUNT = "TimerCount"
    private val FLAG_RUNNING_TIMER = "RunningTimer"

    private var viewRoot: View? = null

    //provides a time
    lateinit var coutingTime: CoutingTime


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val orientation = activity?.resources?.configuration?.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            viewRoot = inflater.inflate(R.layout.fragment_timer, container, false)
        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            viewRoot = inflater.inflate(R.layout.fragment_timer_horizontal, container, false)


        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val startCoutingTime = viewRoot?.findViewById<Button>(R.id.start_countring_time)
        val pauseCoutingTime = viewRoot?.findViewById<Button>(R.id.pause_countring_time)
        val stopCoutingTime = viewRoot?.findViewById<Button>(R.id.stop_countring_time)

        val chronometerEmployment = viewRoot?.findViewById<Chronometer>(R.id.chronometer_employment)

        coutingTime = CoutingTime(chronometerEmployment)

        //restore state
        //get time counter value
        savedInstanceState.let {
            val pauseOffset = savedInstanceState?.getLong(FLAG_TIMER_COUNT)
            val running = savedInstanceState?.getBoolean(FLAG_RUNNING_TIMER)
            savedInstanceState?.putLong(FLAG_TIMER_COUNT, 0)
            if (pauseOffset != null) {
                coutingTime.pauseOffset = pauseOffset
            }

            if (running != null) {
                coutingTime.running = running
            }

            coutingTime.restartChronometr()
        }

        startCoutingTime?.setOnClickListener(this)
        pauseCoutingTime?.setOnClickListener(this)
        stopCoutingTime?.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_countring_time -> coutingTime.startCount()
            R.id.pause_countring_time -> coutingTime.pauseCounting()
            R.id.stop_countring_time -> coutingTime.stopCounting()
        }
    }

    override fun onPause() {
        super.onPause()
        //set the time elapsed since the start of the timer
        coutingTime.setCurrentTime()
        //change flag - timer execution is paused to restart
        coutingTime.running = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(FLAG_TIMER_COUNT, coutingTime.pauseOffset)
        outState.putBoolean(FLAG_RUNNING_TIMER, coutingTime.running)
        super.onSaveInstanceState(outState)
    }


}
