package com.example.myperformance.ui.chart


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myperformance.data.model.CriterionChart
import java.lang.Exception


class TabsPageAdapter(fm: FragmentManager, private val countItems: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DailyProductivityFragment(CriterionChart.TODAY)
            1 -> DailyProductivityFragment(CriterionChart.WEEK)
            2 -> DailyProductivityFragment(CriterionChart.ALL)
            else -> throw Exception("Error in selection of fragment")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "title $position"
    }

    override fun getCount(): Int = 3
}