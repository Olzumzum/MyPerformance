package com.example.myperformance.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.myperformance.R
import com.example.myperformance.broadcast.Restarter
import com.example.myperformance.ui.activities.ScrolbarActivity
import java.util.*

class StopwatchService : Service() {
    private val TIMER_INTENT_ACTION = "example.myperformance"
    val NOTIFICATION_CHANEL_ID = "example.myperformance"
    private val RESTART_SERVICE = "restartservice"
    val chanelName = "Stopwatch Service"
    private val TIME_VALUE_EXTRA = "StopwatchValue"
    private var runningStopwatchFrlag: Boolean = false


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
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)


        //to open an activity with a timer
        val notificationIntent: Intent = Intent(applicationContext, ScrolbarActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)


        val notificationBuilder: Notification.Builder = Notification.Builder(applicationContext, NOTIFICATION_CHANEL_ID)
        val notification = notificationBuilder.setOngoing(true)
                .setContentTitle(getString(R.string.notification_title))
                .setSmallIcon(R.drawable.round_more_time_black_18dp)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(contentIntent)
                .setColor(Color.YELLOW)
                .build()
        startForeground(2, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extras = intent?.extras
        val valueTime = extras?.getInt("time")
        startStopwatch(valueTime)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startStopwatch(valueTime: Int?) {
        if (valueTime != null) {
            runningStopwatchFrlag = true

            var time: Int = valueTime
            val timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {

                    val intent = Intent(TIMER_INTENT_ACTION)
                    time--

                    intent.putExtra(TIME_VALUE_EXTRA, time)
                    application.applicationContext.sendBroadcast(intent)

                    if (time == 0) {
                        timer.cancel()
                        runningStopwatchFrlag = false
                    }
                }
            }, 0, 1000)
        }

    }

    override fun onDestroy() {
        if (runningStopwatchFrlag) {
            val intent = Intent(RESTART_SERVICE)
            intent.setClass(this, Restarter::class.java)
            this.sendBroadcast(intent)
        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}