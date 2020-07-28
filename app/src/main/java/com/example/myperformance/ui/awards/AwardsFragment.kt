package com.example.myperformance.ui.awards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myperformance.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar_scrolbar.*

class AwardsFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val viewRoot = inflater.inflate(R.layout.fragment_awards, container, false)
        val bottomBar = viewRoot?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val nav = findNavController()
        NavigationUI.setupWithNavController(bottomBar!!, nav)

        return viewRoot
    }
}
