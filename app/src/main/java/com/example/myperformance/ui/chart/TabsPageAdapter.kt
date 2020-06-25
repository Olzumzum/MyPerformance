package com.example.myperformance.ui.chart


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myperformance.EmptyFragment1
import com.example.myperformance.EmptyFragment2
import com.example.myperformance.EmptyFragment3
import java.lang.Exception


class TabsPageAdapter(fm: FragmentManager, private val countItems: Int): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return DailyProductivityFragment()
            1 -> return DailyProductivityFragment()
            2 -> return DailyProductivityFragment()
            else -> throw Exception("Error in selection of fragment")
        }
    }

    override fun getCount(): Int = countItems
}