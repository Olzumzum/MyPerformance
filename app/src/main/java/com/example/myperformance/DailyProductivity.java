package com.example.myperformance;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyProductivity extends AppCompatActivity {

    private XYPlot plot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);


        Number[] f = {15, 2, 3, 4};
        Number[] f1 = {1, 2, 3, 4};

        plot = findViewById(R.id.plot);

        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(f), Arrays.asList(f1), "День");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
        plot.addSeries(series1, series1Format);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
