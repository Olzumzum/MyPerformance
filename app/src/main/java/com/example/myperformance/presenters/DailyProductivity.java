package com.example.myperformance.presenters;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.ZoomEstimator;
import com.example.myperformance.R;
import com.example.myperformance.workCharts.ChartDataHolder;
import com.example.myperformance.workCharts.ReturningDataChart;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DailyProductivity extends AppCompatActivity {

    private List<Double> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();
    private static final String MONTH_TITLE_SERIAS = "Производительность";
    PanZoom panZoom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_productivity);
        LinearLayout plotLayout = findViewById(R.id.plot_layout);
//        plotLayout.setOnTouchListener((View.OnTouchListener) this);

        XYPlot plot = findViewById(R.id.plot);

        //получить данные для отображения на графике
        getData();

        //оформление кривой графика
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),
                Color.rgb(0, 100, 0),
                Color.CYAN,
                null);
        //оформление градиентной заливки для графика
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient
                (0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));

        series1Format.setFillPaint(lineFill);

        //определение данных по которым строится кривая и название кривой
        XYSeries series1 = new SimpleXYSeries(keyDate, valueTime, MONTH_TITLE_SERIAS);

        //настройка отображения осей графика
        //получить количество итераций по оси Х (количество дней, для которых строится график)
        final int NUM_GRIDLINES = valueTime.size();

        //Domain
        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, NUM_GRIDLINES);
        //шаг по оси Х
        plot.setDomainStepValue(1);

        //Range
        //настройка шага по осям (чтобы измерения были не десятичными, а целыми)
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("0"));
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new SimpleDateFormat("dd.MM"));
        //установить начало оси Х
//        plot.setUserDomainOrigin(keyDate.get(0));
        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);

        plot.setRangeLabel("");
        plot.setDomainLabel("");

        plot.addSeries(series1, series1Format);

        panZoom = PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.MIN_TICKS);
        plot.getRegistry().setEstimator(new ZoomEstimator());
        plot.getOuterLimits().set(0, 3000, 0, 1000);

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
