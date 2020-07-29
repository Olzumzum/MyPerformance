package com.example.myperformance.ui.chart


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myperformance.data.model.CriterionChart
import java.lang.Exception


class TabsPageAdapter(fm: FragmentManager, private val countItems: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DailyProductivityFragment.newInstance(position)
            1 -> DailyProductivityFragment.newInstance(position)
            2 -> DailyProductivityFragment.newInstance(position)
            else -> throw Exception("Error in selection of fragment")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "title $position"
    }

    override fun getCount(): Int = 3
}