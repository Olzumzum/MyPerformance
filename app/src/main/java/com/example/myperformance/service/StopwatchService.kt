package com.example.myperformance.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myperformance.R
import com.example.myperformance.broadcast.Restarter
import com.example.myperformance.ui.activities.ScrolbarActivity
import java.util.*

class StopwatchService : Service() {
    private val TIMER_INTENT_ACTION = "example.myperformance"
    private val NOTIFICATION_CHANEL_ID = "example.myperformance"
    private val RESTART_SERVICE = "restartservice"
    private val chanelName = "Stopwatch Service"
    private val TIME_VALUE_EXTRA = "StopwatchValue"
    private val START_STOPWATCH_NOTIF_ID = 3
    private val FINISH_STOPWATCH_NOTIF_ID = 4


    private lateinit var chan: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: Notification.Builder
    private lateinit var notificationIntent: Intent
    private lateinit var contentIntent: PendingIntent

    private var timer: Timer = Timer()
    private var runningStopwatchFrlag: Boolean = false
    private var time: Int = 0

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startForeground()
        else
            startForeground(1, Notification())
    }

    //настройки для таймера
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationBuild() {
        chan = NotificationChannel(
                NOTIFICATION_CHANEL_ID, chanelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(chan)


        //to open an activity with a timer
        notificationIntent = Intent(applicationContext, ScrolbarActivity::class.java)
        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        contentIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)


        notificationBuilder = Notification.Builder(applicationContext, NOTIFICATION_CHANEL_ID)
    }

    //уведомить, что секундомер работает
    @RequiresApi(Build.VERSION_CODES.O)
    fun startForeground() {
        //настройка для работы таймера
        notificationBuild()

        //уведомление, что таймер работает
        val notificationStart = notificationBuilder.setOngoing(true)
                .setContentTitle(getString(R.string.stopwatch_title_notification))
                .setSmallIcon(R.drawable.round_more_time_black_18dp)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(contentIntent)
                .setColor(Color.YELLOW)
                .build()
        startForeground(START_STOPWATCH_NOTIF_ID, notificationStart)
    }

    //уведомить, что секундомер завершил работу
    @RequiresApi(Build.VERSION_CODES.O)
    private fun finishStopwatch() {
        //отменить уведомление о том, что таймер считает
        notificationManager.cancel(START_STOPWATCH_NOTIF_ID)

        //уведомление о финише
        val notificationFinish = notificationBuilder.setOngoing(true)
                .setContentTitle(getString(R.string.stopwatch_finished_title_notification))
                .setSmallIcon(R.drawable.round_more_time_black_18dp)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(contentIntent)
                .setColor(Color.YELLOW)
                .setAutoCancel(true)
                .build()
        notificationFinish.flags = Notification.FLAG_INSISTENT
        startForeground(FINISH_STOPWATCH_NOTIF_ID, notificationFinish)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extras = intent?.extras
        val valueTime = extras?.getInt("time")
        startStopwatch(valueTime)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startStopwatch(valueTime: Int?) {
        if (valueTime != null) {


            time = valueTime
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    runningStopwatchFrlag = true
                    val intent = Intent(TIMER_INTENT_ACTION)
                    time--

                    intent.putExtra(TIME_VALUE_EXTRA, time)
                    intent.putExtra("runningStopwatchFrlag", runningStopwatchFrlag)
                    application.applicationContext.sendBroadcast(intent)

                    if (time == 0) {
                        stopStopwatch()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                            finishStopwatch()
                        }
                    }
                }
            }, 0, 1000)
        }

    }

    fun stopStopwatch() {
        timer.let {
            timer.cancel()
            runningStopwatchFrlag = false

        }
    }

    override fun onDestroy() {
        stopStopwatch()
//        if (runningStopwatchFrlag) {
//            val intent = Intent(RESTART_SERVICE)
//            intent.setClass(this, Restarter::class.java)
//            this.sendBroadcast(intent)
//        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("MyLog", "bind")
        return null
    }
}