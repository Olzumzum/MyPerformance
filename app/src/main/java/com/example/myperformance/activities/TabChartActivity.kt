package com.example.myperformance.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration

import com.example.myperformance.R
import com.example.myperformance.ui.chart.TabsPageAdapter
import com.google.android.material.tabs.TabLayout


import kotlinx.android.synthetic.main.activity_tab_chart.*


class TabChartActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

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
