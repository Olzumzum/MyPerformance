package com.example.myperformance.ui.chart


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myperformance.data.model.CriterionChart
import java.lang.Exception


class TabsPageAdapter(fm: FragmentManager, private val countItems: Int) : FragmentStatePagerAdapter(fm) {
    val mapTab: HashMap<CriterionChart, DailyProductivityFragment>

    init {
        val tab1 = DailyProductivityFragment(CriterionChart.TODAY)
        val tab2 = DailyProductivityFragment(CriterionChart.WEEK)
        val tab3 = DailyProductivityFragment(CriterionChart.ALL)
        mapTab = hashMapOf(
                CriterionChart.TODAY to tab1,
                CriterionChart.WEEK to tab2,
                CriterionChart.ALL to tab3)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> mapTab[CriterionChart.TODAY] as Fragment
            1 -> mapTab[CriterionChart.WEEK] as Fragment
            2 -> mapTab[CriterionChart.ALL] as Fragment
            else -> throw Exception("Error in selection of fragment")
        }
    }

    override fun getCount(): Int = countItems
}