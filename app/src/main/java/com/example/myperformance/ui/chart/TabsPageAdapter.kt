package com.example.myperformance.ui.chart

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myperformance.EmptyFragment1
import com.example.myperformance.EmptyFragment2
import com.example.myperformance.EmptyFragment3
import java.lang.NullPointerException

class TabsPageAdapter(fm: FragmentManager, private val countItems: Int): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                Log.d("MyLog", "Empty Fragment 1")
                return EmptyFragment1()
            }
            1 -> {
                Log.d("MyLog", "Empty Fragment 2")
                return EmptyFragment2()
            }
            3 -> {
                Log.d("MyLog", "Empty Fragment 3")
                return EmptyFragment3()
            }
            else -> throw NullPointerException()
        }
    }

    override fun getCount(): Int = countItems
}