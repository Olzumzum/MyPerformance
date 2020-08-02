package com.example.myperformance.ui.stopwatch

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myperformance.R
import com.example.myperformance.broadcast.Restarter
import com.example.myperformance.presenters.StopwatchPresenter
import com.example.myperformance.service.StopwatchService
import com.example.myperformance.view.StopwatchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class StopWatchFragment : MvpAppCompatFragment(), StopwatchView {

    private val RESTART_SERVICE = "restartservice"
    private val NAME_CLASS_SERVICE_FLAG:String = "NameClassService"
    private val TIMER_INTENT_ACTION = "example.myperformance"
    private val TIME_VALUE_EXTRA = "StopwatchValue"
    private val START_BUTTON_STOPWATCH_FLAG = "startStopwatch"
    private val STOP_BUTTON_STOPWATCH_FLAG = "stopStopwatch"

    private var timeValue: Int = 0
    private var runningStopwatchFlag: Boolean = false

    @InjectPresenter
    lateinit var presenter: StopwatchPresenter

    lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val viewRoot = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        val bottomBar = viewRoot?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val nav = findNavController()
        NavigationUI.setupWithNavController(bottomBar!!, nav)

        val intentFilter = IntentFilter(TIMER_INTENT_ACTION)
        broadcastReceiver = stopwatchListen()

        activity?.applicationContext?.registerReceiver(broadcastReceiver, intentFilter)


        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        stopwatchButton.setOnClickListener {
           if(runningStopwatchFlag){
               displayStopwathcStop()

           } else {
               displayStopwathcStart()
           }
        }
    }

    override fun onDestroy() {
        if(runningStopwatchFlag){
            val intent = Intent(RESTART_SERVICE)
            intent.putExtra(NAME_CLASS_SERVICE_FLAG, "StopwatchService")
            this.context?.let { intent.setClass(it, Restarter::class.java) }
            context?.sendBroadcast(intent)
        }
        super.onDestroy()
    }

    private fun stopwatchListen(): BroadcastReceiver {
        return object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val timeValue = intent?.getIntExtra(TIME_VALUE_EXTRA, 0)
                if (timeValue != null) {
                    showTime(timeValue)
                } else
                    showError(R.string.time_getting_error)
            }
        }
    }

    /**
     * processing of entered data,
     * launch start UI
     */
    private fun displayStopwathcStart(){
        val value = timeValueStopWatch?.text.toString()
        if(value.isNotEmpty()) {
            try {
                timeValue = value.toInt()
            } catch (e: ClassCastException) {
                showError(R.string.error_cast_stopwatch_message)
            }
//            decrease_time_view.text = timeValue.toString()

            //note that the stopwatch has started
            runningStopwatchFlag = true

            startStopwatch()

            val intent = Intent(context, StopwatchService::class.java)
            intent.putExtra("time", timeValue)
            intent.putExtra("buttonFlag", START_BUTTON_STOPWATCH_FLAG)
            context?.startService(intent)
        }

    }

    /**
     * stop handing
     */
    private fun displayStopwathcStop(){
        //note that the stopwatch is stopped
        runningStopwatchFlag = false

        val intent = Intent(context, StopwatchService::class.java)
        intent.putExtra("buttonFlag", STOP_BUTTON_STOPWATCH_FLAG)
        context?.sendBroadcast(intent)

        stopStopWatch()
        context?.stopService(intent)
    }

    override fun showError(idResource: Int) {
        Toast.makeText(context, getString(idResource), Toast.LENGTH_LONG).show()
    }

    override fun startStopwatch() {
        //скрыть поле ввода
        timeValueStopWatch?.visibility = View.GONE
        //hide input field
        decrease_time_view?.visibility = View.VISIBLE
        //change the text on the button
        stopwatchButton.text = getString(R.string.stop_text_button_stopwatch)
        //clear input field
        timeValueStopWatch.setText("")
        //hide keyboard
        hideKeyboard(this.context, view )

    }

    override fun stopStopWatch() {
        //display time input field
        timeValueStopWatch?.visibility = View.VISIBLE
        //remove counter
        decrease_time_view?.visibility = View.GONE
        //change the text on the button
        stopwatchButton.text = getString(R.string.start_text_button_stopwatch)
        //clear the counter field
        decrease_time_view.text = ""
    }

    override fun showTime(timedValue: Int) {
        decrease_time_view?.setText(timedValue.toString())
    }


    /**
     * hides the keyboard
     */
    private fun hideKeyboard(context: Context?, view: View?){
        val imm:InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }



}
