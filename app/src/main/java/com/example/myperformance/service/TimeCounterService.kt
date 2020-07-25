package com.example.myperformance.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Chronometer
import com.example.myperformance.R
import com.example.myperformance.ui.timer.CountingTimeView
import kotlinx.android.synthetic.main.fragment_timer.view.*
import java.lang.NullPointerException

class TimeCounterService: Service() {

    var countingTimeView: CountingTimeView? = null

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extras = intent?.extras
        Log.e("MyLog", "Мы в сервисе")
        if (extras != null) {
            val e = extras?.getString("chron")
            val chronometer: Chronometer = e as Chronometer
            countingTimeView = CountingTimeView(chronometer)

        } else
            throw NullPointerException("Time search error")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}