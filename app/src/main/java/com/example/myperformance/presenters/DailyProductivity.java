package com.example.myperformance.presenters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.androidplot.xy.XYPlot;
import com.example.myperformance.R;
import com.example.myperformance.viewModel.TimePerformeViewModel;
import com.example.myperformance.workCharts.GraphicPainter;

import java.util.ArrayList;
import java.util.List;


public class DailyProductivity extends AppCompatActivity {

    private List<? extends Number> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();
    private static final String SERIES_TITLE = "Signthings in USA";

    private XYPlot plot1;

    GraphicPainter graphicPainter;

    //viewModel retrieving stored data from a database
//    private ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);

//        plot1 = findViewById(R.id.plot);


        final TimePerformeViewModel viewModel = new ViewModelProvider(this).get(TimePerformeViewModel.class);

    }


}
