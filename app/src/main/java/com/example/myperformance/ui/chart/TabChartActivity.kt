package com.example.myperformance.ui.chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.size
import com.example.myperformance.EmptyFragment1
import com.example.myperformance.EmptyFragment2
import com.example.myperformance.R
import com.google.android.material.tabs.TabLayout

import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_tab_chart.*
import kotlinx.android.synthetic.main.app_bar_scrolbar.*

class TabChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_chart)
//        setSupportActionBar(toolbar_chart)

//        val tabs = findViewById<TabLayout>(R.id.tabs)
        pager.adapter = TabsPageAdapter(supportFragmentManager, tabs.tabCount)

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(object :
        TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab?.position!!
            }
        }
        )

    }
}
