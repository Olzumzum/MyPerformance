package com.example.myperformance.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androidplot.xy.XYPlot;
import com.example.myperformance.R;
import com.example.myperformance.model.TimePerform;
import com.example.myperformance.viewModel.TimePerformViewModel;
import com.example.myperformance.workCharts.ChartDataHolder;
import com.example.myperformance.workCharts.CriterionChart;
import com.example.myperformance.workCharts.GraphicPainter;
import com.example.myperformance.workCharts.ReturningDataChart;

import java.util.ArrayList;
import java.util.List;


public class DailyProductivityFragment extends Fragment {

    private List<? extends Number> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();
    private XYPlot plot1;
    private CriterionChart criterion;

    DailyProductivityFragment(){
        criterion = null;
    }

    DailyProductivityFragment(CriterionChart criterion){
        this.criterion = criterion;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_productivity, container, false);

        plot1 = view.findViewById(R.id.plot);
        final ReturningDataChart rDataChart = new ChartDataHolder();

        //viewModel retrieving stored data from a database
        TimePerformViewModel viewModel = new ViewModelProvider(this).get(TimePerformViewModel.class);
        viewModel.getAllTimePerform().observeForever(new Observer<List<TimePerform>>() {
            @Override
            public void onChanged(List<TimePerform> timePerforms) {
                if (timePerforms.size() != 0) {
                    //get data in a form convenient for dispaying on a chart
                    rDataChart.setList(timePerforms);
                    keyDate = (List<? extends Number>) rDataChart.getListDayOfWeek();
                    valueTime = rDataChart.getListTimeValue();
                    //draw a chart
                    new GraphicPainter().paint(plot1, keyDate, valueTime);
                }
            }
        });

        return view;
    }

}
