package com.example.myperformance.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration

import com.example.myperformance.R
import com.example.myperformance.ui.chart.TabsPageAdapter
import com.google.android.material.tabs.TabLayout


import kotlinx.android.synthetic.main.activity_tab_chart.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * activity storing tabs with graphs
 */
class TabChartActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_chart)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        runBlocking {
            val j = launch {
                pager.adapter = TabsPageAdapter(supportFragmentManager, tabs.tabCount)
            }
            j.join()
        }
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