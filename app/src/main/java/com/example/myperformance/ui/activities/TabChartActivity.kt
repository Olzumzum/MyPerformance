package com.example.myperformance.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration

import com.example.myperformance.R
import com.example.myperformance.data.model.CriterionChart
import com.example.myperformance.ui.chart.DailyProductivityFragment
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

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val pageAdapter = TabsPageAdapter( supportFragmentManager, 3)
        pager.adapter = pageAdapter
        tabs.setupWithViewPager(pager)






    }
}
