package com.example.myperformance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    CoutingTime coutingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Кнопки управления подсчета временем
        Button startCountingTime = findViewById(R.id.start_countring_time);
        Button pauseCountingTime = findViewById(R.id.pause_countring_time);
        Button stopCountingTime = findViewById(R.id.stop_countring_time);

        Chronometer chronometerEmployment = findViewById(R.id.chronometer_employment);

        coutingTime = new CoutingTime(chronometerEmployment);

        startCountingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coutingTime.startCount();
            }
        });

        stopCountingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coutingTime.stopCounting();
            }
        });

        pauseCountingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coutingTime.pauseCounting();
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        long pauseOffset = coutingTime.getPauseOffset();
        boolean running = coutingTime.getRunning();
        outState.putInt("TimeCount", (int) pauseOffset);
        outState.putBoolean("RunningTimer", running);
        Log.d("MyLog", "Положила " + pauseOffset + " " + running);
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        long pauseOffset = savedInstanceState.getInt("TimeCount");
        boolean running = savedInstanceState.getBoolean("RunningTimer");

        coutingTime.setPauseOffset(pauseOffset);
        coutingTime.setRunning(running);
        super.onRestoreInstanceState(savedInstanceState);


    }
}
