package com.example.myperformance.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.myperformance.service.TimeCounterService

/**
 * A service that allows the timer to continue
 * running on destroy
 */
class Restarter : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context?.startForegroundService(Intent(context, TimeCounterService::class.java))
        else
            context?.startService(Intent(context, TimeCounterService::class.java))
    }


}