package com.example.myperformance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Кнопки управления подсчета временем
    private Button startCountingTime;
    private Button pauseCountingTime;
    private Button stopCountingTime;

    //счетчик времени выполнения действия
    private Chronometer chronometerEmployment;
    private boolean running = false;
    private long pauseOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startCountingTime = findViewById(R.id.start_countring_time);
        pauseCountingTime = findViewById(R.id.pause_countring_time);
        stopCountingTime = findViewById(R.id.stop_countring_time);

        chronometerEmployment = findViewById(R.id.chronometer_employment);

        startCountingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCount(v);
            }
        });

        stopCountingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCounting(v);
            }
        });

        pauseCountingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseCounting(v);
            }
        });


    }

    public void startCount(View view){
        if(!running){
            chronometerEmployment.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometerEmployment.start();
            running = true;
        }
    }

    public void pauseCounting(View view){
        if(running){
            chronometerEmployment.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometerEmployment.getBase();
            running = false;
        }
    }

    public void stopCounting(View view){
        chronometerEmployment.stop();
        running = false;
        pauseOffset = 0;
        chronometerEmployment.setBase(SystemClock.elapsedRealtime());
    }
}
