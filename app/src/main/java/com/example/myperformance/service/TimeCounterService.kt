package com.example.myperformance.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*
import kotlin.concurrent.timerTask


class TimeCounterService : Service() {
    private var timeValue: Long = 0
    private lateinit var timer: Timer



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intentExtra = intent?.extras
        val flag = intentExtra?.get("buttonFlag")
        Log.e("MyLog", "flag $flag")

        if (flag == "pause")
            pauseTimer()
        else
            startTimer()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {

        timer.cancel()
        val intent = Intent("Counter")
        intent.putExtra("finishTimeValue", timeValue)
        application.applicationContext.sendBroadcast(intent)

        timeValue = 0;

//        Log.e("MyLog", "Destroy")
        super.onDestroy()
    }

    fun startTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val intent = Intent("Counter")
                timeValue++
                Log.e("MyLog", timeValue.toString())

                intent.putExtra("TimerValue", timeValue)
                application.applicationContext.sendBroadcast(intent)
            }
        }, 0, 1000)

    }

    fun pauseTimer() {
        timer.cancel()
    }
}