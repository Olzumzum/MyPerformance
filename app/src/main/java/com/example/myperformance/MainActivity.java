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

    //обеспечивает работу с таймером
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

        if(savedInstanceState != null){
            long pauseOffset = savedInstanceState.getLong("TimeCount");
            boolean running = savedInstanceState.getBoolean("RunningTimer");
            savedInstanceState.putLong("TimeCount", 0);
                coutingTime.setPauseOffset(pauseOffset);
                coutingTime.setRunning(running);
                coutingTime.restartChronometr();
        }


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
    protected void onPause() {
        super.onPause();
        //установить время, прошедшее с начала запуска таймера
        coutingTime.setCurrentTime();
        //поменять флаг - выполнение таймера приостанавливается для перезапуска
        coutingTime.setRunning(false);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong("TimeCount", coutingTime.getPauseOffset());
        outState.putBoolean("RunningTimer", coutingTime.getRunning());
        super.onSaveInstanceState(outState);
    }

}
