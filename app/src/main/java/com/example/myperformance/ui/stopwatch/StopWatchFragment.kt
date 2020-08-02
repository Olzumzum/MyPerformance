package com.example.myperformance.ui.stopwatch

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myperformance.R
import com.example.myperformance.view.StopwatchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_stopwatch.*

class StopWatchFragment : Fragment(), StopwatchView {

    private var timeValue: Int = 0
    private var running_stopwatch: Boolean = false



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val viewRoot = inflater.inflate(R.layout.fragment_stopwatch, container, false)
        val bottomBar = viewRoot?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val nav = findNavController()
        NavigationUI.setupWithNavController(bottomBar!!, nav)


        return viewRoot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        stopwatchButton.setOnClickListener {
           if(running_stopwatch){
               displayStopwathcStop()

           } else {
               displayStopwathcStars()
           }
        }
    }

    /**
     * processing of entered data,
     * launch start UI
     */
    private fun displayStopwathcStars(){
        val value = timeValueStopWatch?.text.toString()
        if(value.isNotEmpty()) {
            try {
                timeValue = value.toInt()
            } catch (e: ClassCastException) {
                showError(R.string.error_cast_stopwatch_message)
            }
            decrease_time_view.text = timeValue.toString()
            startStopwatch()
        }
    }

    /**
     * stop handing
     */
    private fun displayStopwathcStop(){
        stopStopWatch()

    }

    override fun showError(idResource: Int) {
        Toast.makeText(context, getString(idResource), Toast.LENGTH_LONG).show()
    }

    override fun startStopwatch() {
        //note that the stopwatch has started
        running_stopwatch = true
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
        //note that the stopwatch is stopped
        running_stopwatch = false
        //display time input field
        timeValueStopWatch?.visibility = View.VISIBLE
        //remove counter
        decrease_time_view?.visibility = View.GONE
        //change the text on the button
        stopwatchButton.text = getString(R.string.start_text_button_stopwatch)
        //clear the counter field
        decrease_time_view.text = ""
    }

    override fun showTime() {
        TODO("Not yet implemented")
    }


    /**
     * hides the keyboard
     */
    private fun hideKeyboard(context: Context?, view: View?){
        val imm:InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
