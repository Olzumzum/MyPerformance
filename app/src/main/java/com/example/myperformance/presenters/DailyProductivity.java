package com.example.myperformance.presenters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.XYPlot;
import com.example.myperformance.R;
import com.example.myperformance.workCharts.ChartDataHolder;
import com.example.myperformance.workCharts.GraphicPainter;
import com.example.myperformance.workCharts.ReturningDataChart;

import java.util.ArrayList;
import java.util.List;

public class DailyProductivity extends AppCompatActivity {

    private List<Double> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);

        XYPlot plot = findViewById(R.id.plot);

        //получить данные для отображения на графике
        getData();

        new GraphicPainter().paint(plot, keyDate, valueTime);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //получить данные для отображения на графике
    private void getData() {
        ReturningDataChart rDataChart = new ChartDataHolder();
        keyDate = rDataChart.getListDayOfWeek();
        valueTime = rDataChart.getListTimeValue();
    }

}
