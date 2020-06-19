package com.example.myperformance.presenters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidplot.xy.XYPlot;
import com.example.myperformance.R;
import com.example.myperformance.model.TimePerforme;
import com.example.myperformance.viewModel.TimePerformeViewModel;
import com.example.myperformance.workCharts.ChartDataHolder;
import com.example.myperformance.workCharts.GraphicPainter;
import com.example.myperformance.workCharts.ReturningDataChart;

import java.util.ArrayList;
import java.util.List;


public class DailyProductivityActivity extends AppCompatActivity {

    private List<? extends Number> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();
    private XYPlot plot1;


    //viewModel retrieving stored data from a database
    private TimePerformeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);

        plot1 = findViewById(R.id.plot);
        final ReturningDataChart rDataChart = new ChartDataHolder();

        viewModel = new ViewModelProvider(this).get(TimePerformeViewModel.class);
        viewModel.getAllTimePerforme().observeForever(new Observer<List<TimePerforme>>() {
            @Override
            public void onChanged(List<TimePerforme> timePerformes) {
                if (timePerformes.size() != 0) {
                    //get data in a form convenient for dispaying on a chart
                    rDataChart.setList(timePerformes);
                    keyDate = (List<? extends Number>) rDataChart.getListDayOfWeek();
                    valueTime = rDataChart.getListTimeValue();
                    //draw a chart
                    new GraphicPainter().paint(plot1, keyDate, valueTime);
                }
            }
        });


    }

}
