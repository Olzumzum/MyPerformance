package com.example.myperformance.ui.timer

import android.Manifest.permission.FOREGROUND_SERVICE
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myperformance.R
import com.example.myperformance.app.App
import com.example.myperformance.broadcast.Restarter
import com.example.myperformance.presenters.TimerPresenter
import com.example.myperformance.presenters.viewModel.TimePerformViewModel
import com.example.myperformance.service.TimeCounterService
import com.example.myperformance.view.TimerView
import kotlinx.android.synthetic.main.nav_header_scrolbar.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import java.sql.Time

class TimerFragment : MvpAppCompatFragment(), View.OnClickListener, TimerView {
    //intent action flag
    private val TIMER_INTENT_ACTION = "example.myperformance"
    private val TIME_VALUE_EXTRA = "TimerValue"
    private val FINISH_TIME_VALUE = "finishTimeValue"
    private val BUTTON_ACTION_FLAG = "buttonFlag"
    private val BUTTON_ACTION_START = "start"
    private val BUTTON_ACTION_PAUSE = "pause"
    private val BUTTON_ACTION_STOP = "stope"

    private var finishTimevalue: Long? = 0

    private var viewRoot: View? = null

    lateinit var startCoutingTime: Button
    lateinit var pauseCoutingTime: Button
    lateinit var stopCoutingTime: Button
    lateinit var progressBarTimer: ProgressBar
    lateinit var timerTextView: TextView


    @InjectPresenter
    lateinit var timerPresenter: TimerPresenter

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

        startCoutingTime = viewRoot?.findViewById<Button>(R.id.start_countring_time)!!
        pauseCoutingTime = viewRoot?.findViewById<Button>(R.id.pause_countring_time)!!
        stopCoutingTime = viewRoot?.findViewById<Button>(R.id.stop_countring_time)!!
        progressBarTimer = viewRoot?.findViewById(R.id.progressBarTimer)!!
        timerTextView = viewRoot?.findViewById<TextView>(R.id.timer_employment)!!

        // getting the application to initialize the repository
        timerPresenter.application = activity?.application!!
        timerPresenter.viewModel = ViewModelProvider(this).get(TimePerformViewModel::class.java)



        startCoutingTime.setOnClickListener(this)
        pauseCoutingTime.setOnClickListener(this)
        stopCoutingTime.setOnClickListener(this)

        val intentFilter = IntentFilter(TIMER_INTENT_ACTION)

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val timeValue = intent?.getLongExtra(TIME_VALUE_EXTRA, 0)
                timerTextView.text = timeValue.toString()

                finishTimevalue = intent?.getLongExtra(FINISH_TIME_VALUE, 0)
                finishTimevalue?.let {
                    if (it.compareTo(0) != 0) {
                        timerPresenter.saveTime(it)
                        finishTimevalue = 0
                    }
                }
            }

        }

        activity?.applicationContext?.registerReceiver(broadcastReceiver, intentFilter)
        Log.e("MyLog", "finish value $finishTimevalue")


        return viewRoot
    }




    override fun onClick(v: View?) {
        val intent = Intent(context, TimeCounterService::class.java)
        when (v?.id) {
            R.id.start_countring_time -> {
                intent.putExtra(BUTTON_ACTION_FLAG, BUTTON_ACTION_START)
                    context?.startService(intent)

            }
            R.id.pause_countring_time -> {
                intent.putExtra(BUTTON_ACTION_FLAG, BUTTON_ACTION_PAUSE)
                    context?.startService(intent)
            }
            R.id.stop_countring_time -> {
                intent.putExtra(BUTTON_ACTION_FLAG, BUTTON_ACTION_STOP)
                context?.stopService(intent)

                timerTextView.text = 0.toString()
            }
        }
    }

    override fun onDestroy() {
        val intent = Intent("restartservice")
        this.context?.let { intent.setClass(it, Restarter::class.java) }
        context?.sendBroadcast(intent)
        super.onDestroy()
    }

    override fun showError() {
        Toast.makeText(this.context, R.string.error_saving_data, Toast.LENGTH_SHORT).show()
    }

    override fun saveData() {
        startCoutingTime.isEnabled = false
        pauseCoutingTime.isEnabled = false
        stopCoutingTime.isEnabled = false
        progressBarTimer.visibility = View.VISIBLE
    }

    override fun showButton() {
        startCoutingTime.isEnabled = true
        pauseCoutingTime.isEnabled = true
        stopCoutingTime.isEnabled = true
        progressBarTimer.visibility = View.GONE

    }

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent().inject(this)
        super.onAttach(context)
    }

}
