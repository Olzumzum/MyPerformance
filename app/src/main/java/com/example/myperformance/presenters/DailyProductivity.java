package com.example.myperformance.presenters;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.myperformance.R;
import com.example.myperformance.workCharts.ChartDataHolder;
import com.example.myperformance.workCharts.ReturningDataChart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DailyProductivity extends AppCompatActivity {

    private XYPlot plot;
    private List<Integer> keyDate = new ArrayList();
    private List<Integer> valueTime = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);

        plot = findViewById(R.id.plot);
        plot.setPlotMargins(0,0,0,0);
        plot.setPlotPadding(0,0,0,0);


        //получить данные для отображения на графике
        getData();



        XYSeries series1 = new SimpleXYSeries(
                keyDate, valueTime, "День");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, null, null);


        double[] inc_domain = new double[]{1,2,3};
        double[] inc_range = new double[]{1,5,10,20,50,100};
        final int NUM_GRIDLINES = valueTime.size();
        final int MAX_VALUE_TIME = 20;

//        plot.setDomainStepModel(new StepModelFit(plot.getBounds().getxRegion(),inc_domain,NUM_GRIDLINES));
//        plot.setRangeStepModel( new StepModelFit(plot.getBounds().getyRegion(),inc_range,NUM_GRIDLINES));

        //Domain
        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, NUM_GRIDLINES);
        //шаг по оси Х
        plot.setDomainStepValue(1);

        //Range
        plot.setRangeBoundaries(0, MAX_VALUE_TIME, BoundaryMode.FIXED);
//        plot.setRangeStepValue(1);
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("0"));
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("0"));


        plot.addSeries(series1, series1Format);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //получить данные для отображения на графике
    private void getData(){
        ReturningDataChart rDataChart = new ChartDataHolder();
        keyDate = rDataChart.getListDayOfWeek();
        valueTime = rDataChart.getListTimeValue();
    }
}
