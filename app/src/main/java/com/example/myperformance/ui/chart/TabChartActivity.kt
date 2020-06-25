package com.example.myperformance.ui.chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar

import com.example.myperformance.R
import com.google.android.material.tabs.TabLayout


import kotlinx.android.synthetic.main.activity_tab_chart.*


class TabChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_chart)

        pager.adapter = TabsPageAdapter(supportFragmentManager, tabs.tabCount)
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                pager.currentItem = tab?.position!!
            }

        })
    }
}
