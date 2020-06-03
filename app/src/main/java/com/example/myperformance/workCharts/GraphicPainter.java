package com.example.myperformance.workCharts;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.ZoomEstimator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class GraphicPainter  {
    private static XYPlot plot;
    private  static final String MONTH_TITLE_SERIAS = "Производительность";
    private XYSeries series;
    private int NUM_GRIDLINES;


    private void setDataChart(List<? extends Number> keyDate, List<? extends Number> valueTime){

        //определение данных по которым строится кривая и название кривой
        series = new SimpleXYSeries(keyDate, valueTime, MONTH_TITLE_SERIAS);

        //настройка отображения осей графика
        //получить количество итераций по оси Х (количество дней, для которых строится график)
        NUM_GRIDLINES = valueTime.size();

    }


    private void paintChart(){
        //оформление стиля кривой
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


        //Domain
        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, NUM_GRIDLINES);
        //шаг по оси Х
        plot.setDomainStepValue(1);

        //Range
        //настройка шага по осям (чтобы измерения были не десятичными, а целыми)
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("0"));
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new SimpleDateFormat());
        //установить начало оси Х
//        plot.setUserDomainOrigin(keyDate.get(0));
        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);

        plot.setRangeLabel("");
        plot.setDomainLabel("");

        plot.addSeries(series, series1Format);

        //настройка масштабирования
        PanZoom panZoom = PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.MIN_TICKS);
        plot.getRegistry().setEstimator(new ZoomEstimator());
//        plot.getOuterLimits().set(0, 100, 0, 1000);
    }


    public void paint(XYPlot plot, List<? extends Number> domainData, List<? extends Number> rangeData) {
        this.plot = plot;
        setDataChart(domainData, rangeData);
        paintChart();
    }
}
