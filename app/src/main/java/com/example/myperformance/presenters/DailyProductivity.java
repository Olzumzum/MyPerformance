package com.example.myperformance.presenters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.example.myperformance.R;
import com.example.myperformance.workCharts.ChartDataHolder;
import com.example.myperformance.workCharts.GraphicPainter;
import com.example.myperformance.workCharts.ReturningDataChart;

import java.util.ArrayList;
import java.util.List;


public class DailyProductivity extends AppCompatActivity {

    private List<? extends Number> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();
    private static final String SERIES_TITLE = "Signthings in USA";

    private XYPlot plot1;
    private SimpleXYSeries series;

    GraphicPainter graphicPainter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);

        plot1 = findViewById(R.id.plot);

        getData();
        graphicPainter = new GraphicPainter();
        graphicPainter.paint(plot1, keyDate, valueTime);

    }


    //получить данные для отображения на графике
    private void getData() {
        ReturningDataChart rDataChart = new ChartDataHolder();
        keyDate = (List<? extends Number>) rDataChart.getListDayOfWeek();
        valueTime = rDataChart.getListTimeValue();
    }

}
