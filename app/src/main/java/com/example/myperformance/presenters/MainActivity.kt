package com.example.myperformance.presenters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.example.myperformance.R
import com.example.myperformance.worktime.CoutingTime

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val FLAG_TIMER_COUNT = "TimerCount"
    private val FLAG_RUNNING_TIMER = "RunningTimer"

    //provides a time
    lateinit var coutingTime: CoutingTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startCoutingTime = findViewById<Button>(R.id.start_countring_time)
        val pauseCoutingTime = findViewById<Button>(R.id.pause_countring_time)
        val stopCoutingTime = findViewById<Button>(R.id.stop_countring_time)

        val chronometerEmployment = findViewById<Chronometer>(R.id.chronometer_employment)

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

        startCoutingTime.setOnClickListener(this)
        pauseCoutingTime.setOnClickListener(this)
        stopCoutingTime.setOnClickListener(this)

        val grathact = findViewById<Button>(R.id.grath_activity)
        grathact.setOnClickListener {
            val intent = Intent(this@MainActivity, DailyProductivityActivity::class.java)
            startActivity(intent)
        }

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
