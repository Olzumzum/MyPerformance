package com.example.myperformance.workCharts;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GraphicPainter  {

    private  static final String MONTH_TITLE_SERIAS = "Производительность";
    private SimpleXYSeries series;
    private int NUM_GRIDLINES;
    private List<? extends Number> keyDate = new ArrayList<>();
    private List<? extends Number> valueTime = new ArrayList<>();
    Bundle savedInstanceState;

    private static final String SERIES_TITLE = "Signthings in USA";

    private XYPlot plot1;

    final Date[] years = {
            new GregorianCalendar(2001, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2001, Calendar.JULY, 1).getTime(),
            new GregorianCalendar(2002, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2002, Calendar.JULY, 1).getTime(),
            new GregorianCalendar(2003, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2003, Calendar.JULY, 1).getTime(),
            new GregorianCalendar(2004, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2004, Calendar.JULY, 1).getTime(),
            new GregorianCalendar(2005, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2005, Calendar.JULY, 1).getTime()
    };

    private void setDataChart(List<? extends Number> keyDate, List<? extends Number> valueTime){

        this.keyDate = keyDate;
        this.valueTime = valueTime;

        //настройка отображения осей графика
        //получить количество итераций по оси Х (количество дней, для которых строится график)
        NUM_GRIDLINES = valueTime.size();

    }


    private void paintChart(){
        addSeries(savedInstanceState);

        plot1.setRangeBoundaries(0, 10, BoundaryMode.FIXED);

        plot1.getGraph().getGridBackgroundPaint().setColor(Color.WHITE);
        plot1.getGraph().getDomainGridLinePaint().setColor(Color.BLACK);
        plot1.getGraph().getDomainGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraph().getRangeGridLinePaint().setColor(Color.BLACK);
        plot1.getGraph().getRangeGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraph().getDomainOriginLinePaint().setColor(Color.BLACK);
        plot1.getGraph().getRangeOriginLinePaint().setColor(Color.BLACK);

        plot1.getGraph().setPaddingRight(2);

        // draw a domain tick for each year:
        plot1.setDomainStep(StepMode.SUBDIVIDE, years.length);

        // customize our domain/range labels
        plot1.setDomainLabel("Year");
        plot1.setRangeLabel("# of Sightings");

        // get rid of decimal points in our range labels:
        plot1.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).
                setFormat(new DecimalFormat("0"));

        plot1.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new Format() {


                    @SuppressLint("SimpleDateFormat")
                    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");

                    @Override
                    public StringBuffer format(Object obj,
                                               @NonNull StringBuffer toAppendTo,
                                               @NonNull FieldPosition pos) {

                        // this rounding is necessary to avoid precision loss when converting from
                        // double back to int:
                        int yearIndex = (int) Math.round(((Number) obj).doubleValue());
                        return dateFormat.format(keyDate.get(yearIndex), toAppendTo, pos);
                    }

                    @Override
                    public Object parseObject(String source, @NonNull ParsePosition pos) {
                        return null;

                    }
                });


        //настройка масштабирования
//        PanZoom panZoom = PanZoom.attach(plot1, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.MIN_TICKS);
//        plot1.getRegistry().setEstimator(new ZoomEstimator());
    }

    public void addSeries(Bundle savedInstanceState) {
        Number[] yVals;

        if(savedInstanceState != null) {
            yVals = (Number[]) savedInstanceState.getSerializable(SERIES_TITLE);
        } else {
            yVals = new Number[]{5, 8, 6, 9, 3, 8, 5, 4, 7, 4};
        }

        // create our series from our array of nums:
        series = new SimpleXYSeries(Arrays.asList(yVals),
                valueTime, SERIES_TITLE);

        LineAndPointFormatter formatter =
                new LineAndPointFormatter(Color.rgb(0, 0, 0), Color.RED, Color.RED, null);
        formatter.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
        formatter.getLinePaint().setStrokeWidth(PixelUtils.dpToPix(5));

        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);

        formatter.setFillPaint(lineFill);

        plot1.addSeries(series, formatter);
    }

    public Bundle getSavedInstanceState(){
        return savedInstanceState;
    }

    public SimpleXYSeries getSeries(){return series;}

    public void paint(Bundle savedInstanceState, XYPlot plot, List<? extends Number> domainData, List<? extends Number> rangeData) {
        this.savedInstanceState = savedInstanceState;
        plot1 = plot;
        setDataChart(domainData, rangeData);
        paintChart();
    }
}
