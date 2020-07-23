package com.example.myperformance.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myperformance.R

class PersonalAreaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_area)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}