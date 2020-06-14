package com.example.myperformance.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myperformance.R;
import com.example.myperformance.worktime.CoutingTime;

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

        Button grathact = findViewById(R.id.grath_activity);
        grathact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyProductivity.class );
                startActivity(intent);
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
