package com.example.myperformance.ui.timer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Chronometer
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myperformance.R
import com.example.myperformance.worktime.CoutingTime

class TimerFragment : Fragment(), View.OnClickListener, View.OnTouchListener {
    private val FLAG_TIMER_COUNT = "TimerCount"
    private val FLAG_RUNNING_TIMER = "RunningTimer"

    private var viewRoot: View? = null
    lateinit var startCoutingTime: Button

    //provides a time
    lateinit var coutingTime: CoutingTime


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


        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startCoutingTime = viewRoot?.findViewById<Button>(R.id.start_countring_time)!!
        val pauseCoutingTime = viewRoot?.findViewById<Button>(R.id.pause_countring_time)
        val stopCoutingTime = viewRoot?.findViewById<Button>(R.id.stop_countring_time)

        val chronometerEmployment = viewRoot?.findViewById<Chronometer>(R.id.chronometer_employment)

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

        this.startCoutingTime.setOnClickListener(this)
        pauseCoutingTime?.setOnClickListener(this)
        stopCoutingTime?.setOnClickListener(this)


    }

    private fun colorize(v: View) {
        val c = context?.let { ContextCompat.getColor(it, R.color.button_pressure) }
        val k = context?.let { ContextCompat.getColor(it, R.color.MyPerfDark) }

        if (c != null && k != null) {
            val animator = ObjectAnimator.ofInt(v, "backgroundColor",
                    c, k)
            animator.duration = 200
            animator.repeatCount = 1
            animator.repeatMode = ObjectAnimator.REVERSE
//        animator.disableViewDuringAnimation(startCoutingTime)
            animator.start()
        }
        Log.d("MyLog", "Мы были в анимации")
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_countring_time -> {
                coutingTime.startCount()
                colorize(v)

            }
            R.id.pause_countring_time -> {

                coutingTime.pauseCounting()
                colorize(v)
            }
            R.id.stop_countring_time -> {
                coutingTime.stopCounting()
                colorize(v)
            }
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


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val scaleUp = AnimationUtils.loadAnimation(this.activity, R.anim.button_up)
        val scaleDown = AnimationUtils.loadAnimation(this.activity, R.anim.button_down)

        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                v?.startAnimation(scaleUp)


            }
            MotionEvent.ACTION_BUTTON_RELEASE -> {
                v?.startAnimation(scaleDown)

            }
        }
        return true
    }


}
