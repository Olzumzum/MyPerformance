package com.example.myperformance.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.*
import com.example.myperformance.R
import kotlinx.android.synthetic.main.activity_scrolbar.*
import kotlinx.android.synthetic.main.app_bar_scrolbar.*
import kotlinx.android.synthetic.main.nav_header_scrolbar.view.*

class ScrolbarActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolbar)
        setSupportActionBar(toolbar)

        //nav controller
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_timer, R.id.nav_awards, R.id.nav_app_info), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val header = navView.getHeaderView(0)
        header.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> {
                        startActivity(Intent(applicationContext, PersonalAreaActivity::class.java))
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
