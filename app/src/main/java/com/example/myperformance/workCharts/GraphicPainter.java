package com.example.myperformance.workCharts;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.androidplot.Plot;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.ZoomEstimator;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GraphicPainter  {
    private final String NAME_AXIS_X = "Дата";
    private final String NAME_AXIS_Y = "Время";
    private  static final String MONTH_TITLE_SERIAS = "Производительность";

    private SimpleXYSeries series;
    private List<? extends Number> keyDate = new ArrayList<>();
    private List<? extends Number> valueTime = new ArrayList<>();


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

    }


    private void paintChart(){
        addSeries();

        plot1.setRangeBoundaries(0, 10, BoundaryMode.GROW);
        plot1.setDomainBoundaries(0, 7, BoundaryMode.FIXED);
        plot1.setBorderStyle(Plot.BorderStyle.NONE, null, null);

        plot1.getGraph().setPaddingRight(2);

        // draw a domain tick for each year:
        plot1.setDomainStep(StepMode.SUBDIVIDE, keyDate.size());

        // customize our domain/range labels
        plot1.setDomainLabel(NAME_AXIS_X);
        plot1.setRangeLabel(NAME_AXIS_Y);

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

                        int yearIndex = (int) Math.round(((Number) obj).doubleValue());
                        return dateFormat.format(keyDate.get(yearIndex), toAppendTo, pos);
                    }

                    @Override
                    public Object parseObject(String source, @NonNull ParsePosition pos) {
                        return null;

                    }
                });


        //настройка масштабирования
        PanZoom panZoom = PanZoom.attach(plot1, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.MIN_TICKS);
        plot1.getRegistry().setEstimator(new ZoomEstimator());
//        plot1.getOuterLimits().set(0, keyDate.size(), 0, 20);
    }

    public void addSeries() {



        // create our series from our array of nums:
        series = new SimpleXYSeries(valueTime,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, MONTH_TITLE_SERIAS);

        LineAndPointFormatter series1Format  =
                new LineAndPointFormatter(Color.rgb(0, 200, 0),
                        Color.rgb(0, 100, 0),
                        Color.CYAN,
                        null);

        series1Format .getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
        series1Format .getLinePaint().setStrokeWidth(PixelUtils.dpToPix(5));

        //оформление градиентной заливки для графика
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient
                (0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));

        series1Format.setFillPaint(lineFill);

        plot1.addSeries(series, series1Format );
    }

    public void paint(XYPlot plot, List<? extends Number> domainData, List<? extends Number> rangeData) {
        plot1 = plot;
        setDataChart(domainData, rangeData);
        paintChart();
    }
}
