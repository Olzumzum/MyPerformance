package com.example.myperformance.ui.stopwatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myperformance.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class StopWatchFragment : Fragment() {


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
}
