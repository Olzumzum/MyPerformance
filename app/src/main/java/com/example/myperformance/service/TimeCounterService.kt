package com.example.myperformance.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myperformance.broadcast.Restarter
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Timer service.
 * Ensures that the timer operates in background
 */
class TimeCounterService : Service() {
    private val TIMER_INTENT_ACTION = "example.myperformance"
    private val TIME_VALUE_EXTRA = "TimerValue"
    private val FINISH_TIME_VALUE = "finishTimeValue"
    private val BUTTON_ACTION_FLAG = "buttonFlag"
    private val BUTTON_ACTION_START = "start"
    private val BUTTON_ACTION_PAUSE = "pause"
    private val BUTTON_ACTION_STOP = "stop"
    val NOTIFICATION_CHANEL_ID = "example.myperformance"
    private val RESTART_SERVICE = "restartservice"
    val chanelName = "Background Service"

    private var timeValue: Long = 0
    private var timer: Timer? = null

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startForeground()
        else
            startForeground(1, Notification())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startForeground() {
        val chan: NotificationChannel = NotificationChannel(
                NOTIFICATION_CHANEL_ID, chanelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.YELLOW
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)

        val notificationBuilder: Notification.Builder = Notification.Builder(this, NOTIFICATION_CHANEL_ID)
        val notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(Notification.PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
        startForeground(2, notification)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intentExtra = intent?.extras
        val flag = intentExtra?.get(BUTTON_ACTION_FLAG)
        when (flag) {
            BUTTON_ACTION_START -> startTimer()
            BUTTON_ACTION_PAUSE -> pauseTimer()
            BUTTON_ACTION_STOP -> stopTimer()

        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {

        stopTimer()

        if(timer != null) {
            val intent = Intent(RESTART_SERVICE)
            intent.setClass(this, Restarter::class.java)
            this.sendBroadcast(intent)
        }

        timeValue = 0;
        super.onDestroy()
    }

    private fun startTimer() {
        timer = Timer()
        timer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val intent = Intent(TIMER_INTENT_ACTION)
                timeValue++

                intent.putExtra(TIME_VALUE_EXTRA, timeValue)
                application.applicationContext.sendBroadcast(intent)
            }
        }, 0, 1000)

    }

    private fun pauseTimer() {
        timer?.cancel()
    }

    private fun stopTimer() {
        timer.let {
            timer?.cancel()
            timer = null
        }
        val intent = Intent(TIMER_INTENT_ACTION)
        intent.putExtra(FINISH_TIME_VALUE, timeValue)
        this.sendBroadcast(intent)
    }
}