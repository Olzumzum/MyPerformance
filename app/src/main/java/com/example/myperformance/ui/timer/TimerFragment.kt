package com.example.myperformance.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myperformance.R
import com.example.myperformance.worktime.CoutingTime

class TimerFragment : Fragment(), View.OnClickListener {
    private val FLAG_TIMER_COUNT = "TimerCount"
    private val FLAG_RUNNING_TIMER = "RunningTimer"

    //provides a time
    lateinit var coutingTime: CoutingTime


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_timer, container, false)


        return root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        val startCoutingTime = findViewById<Button>(R.id.start_countring_time)
//        val pauseCoutingTime = container?.findViewById<Button>(R.id.pause_countring_time)
//        val stopCoutingTime = container?.findViewById<Button>(R.id.stop_countring_time)
//
//        val chronometerEmployment = container?.findViewById<Chronometer>(R.id.chronometer_employment)
//
//        coutingTime = CoutingTime(chronometerEmployment)
//
//        //restore state
//        //get time counter value
//        savedInstanceState.let {
//            val pauseOffset = savedInstanceState?.getLong(FLAG_TIMER_COUNT)
//            val running = savedInstanceState?.getBoolean(FLAG_RUNNING_TIMER)
//            savedInstanceState?.putLong(FLAG_TIMER_COUNT, 0)
//            if (pauseOffset != null) {
//                coutingTime.pauseOffset = pauseOffset
//            }
//
//            if (running != null) {
//                coutingTime.running = running
//            }
//
//            coutingTime.restartChronometr()
//        }
//
//        startCoutingTime?.setOnClickListener(this)
//        pauseCoutingTime?.setOnClickListener(this)
//        stopCoutingTime?.setOnClickListener(this)
//
//
//    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_countring_time -> coutingTime.startCount()
            R.id.pause_countring_time -> coutingTime.pauseCounting()
            R.id.stop_countring_time -> coutingTime.stopCounting()
        }
    }

}
