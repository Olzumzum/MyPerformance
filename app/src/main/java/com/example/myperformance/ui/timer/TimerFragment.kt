package com.example.myperformance.ui.timer

import android.Manifest.permission.FOREGROUND_SERVICE
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
import com.example.myperformance.presenters.TimerPresenter
import com.example.myperformance.presenters.viewModel.TimePerformViewModel
import com.example.myperformance.service.TimeCounterService
import com.example.myperformance.view.TimerView
import kotlinx.android.synthetic.main.nav_header_scrolbar.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class TimerFragment : MvpAppCompatFragment(), View.OnClickListener, TimerView {
    private val FLAG_TIMER_COUNT = "TimerCount"
    private val FLAG_RUNNING_TIMER = "RunningTimer"

    private var viewRoot: View? = null

    lateinit var startCoutingTime: Button
    lateinit var pauseCoutingTime: Button
    lateinit var stopCoutingTime: Button
    lateinit var progressBarTimer: ProgressBar
    lateinit var timerTextView: TextView

    @InjectPresenter
    lateinit var timerPresenter: TimerPresenter

    private var finishTimevalue: Long? = 0


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


        val intentFilter = IntentFilter("Counter")

        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val timeValue = intent?.getLongExtra("TimerValue", 0)
                timerTextView.text = timeValue.toString()

                finishTimevalue = intent?.getLongExtra("finishTimeValue", 0)
                finishTimevalue?.let {
                    if (it.compareTo(0) != 0) {
                        timerPresenter.saveTime(it)
                        finishTimevalue = 0
                    }

                }

//                Log.e("MyLog", "finishvalue $finishTimevalue")
            }


        }

        activity?.applicationContext?.registerReceiver(broadcastReceiver, intentFilter)

        return viewRoot
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_countring_time -> {
                val intent = Intent(context, TimeCounterService::class.java)
                intent.putExtra("buttonFlag", "start")
                context?.startService(intent)

            }
            R.id.pause_countring_time -> {
                val intent = Intent(context, TimeCounterService::class.java)
                intent.putExtra("buttonFlag", "pause")
                context?.startService(intent)
            }
            R.id.stop_countring_time -> {
                val intent = Intent(context, TimeCounterService::class.java)
                intent.putExtra("buttonFlag", "stop")
                context?.stopService(intent)

                timerTextView.text = 0.toString()
            }

        }


    }

    override fun onPause() {
        super.onPause()
//        //set the time elapsed since the start of the timer
//        coutingTimeView.setCurrentTime()
//        //change flag - timer execution is paused to restart
//        coutingTimeView.running = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putLong(FLAG_TIMER_COUNT, coutingTimeView.pauseOffset)
//        outState.putBoolean(FLAG_RUNNING_TIMER, coutingTimeView.running)
        super.onSaveInstanceState(outState)
    }

    override fun showError() {
        Toast.makeText(this.context, "Error saving data", Toast.LENGTH_SHORT).show()
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
