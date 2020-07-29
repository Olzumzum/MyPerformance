package com.example.myperformance.ui.chart;

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
import java.util.List;

final public class GraphicPainter {
    private final String NAME_AXIS_X = "Дата";
    private final String NAME_AXIS_Y = "Время";
    private static final String MONTH_TITLE_SERIAS = "Производительность";

    private int MIN_VALUE_X = 0;
    private int MAX_VALUE_X;
    private int MIN_VALUE_Y = 0;
    private int MAX_VALUE_Y = 0;

    private XYPlot plot;
    private List<Number> keyDate = new ArrayList<>();
    private List<Number> valueTime = new ArrayList<>();

    private SimpleDateFormat dateFormat;

    /**
     * call chart rendering functions
     *@param plot - transfer space for plotting
     *@param domainData - data range on the X axis
     *@param rangeData - data range on the y axis
     */
    public void paint(XYPlot plot, List<Number> domainData, List<Number> rangeData) {
        this.plot = plot;
        setDataChart(domainData, rangeData);
        paintChart();
    }

    /**
     * set the data needed to plot
     *@param keyDate - X-axis data
     *@param valueTime - data on the Y axis
     */
    private void setDataChart(List<Number> keyDate, List<Number> valueTime) {
        this.keyDate = keyDate;
        this.valueTime = valueTime;

        //calculation of the range of the graph (minimum and maximum values)
        //to scale
        MAX_VALUE_X = keyDate.size();
        MAX_VALUE_Y = searchMaxValue(valueTime);
    }


    /**
     * find the maximum item in the collection for proper scaling
     *@param list - collection in which maximum search is performed
     *@return int-element - the maximum element of the collection
     */
    @org.jetbrains.annotations.Contract("null -> fail")
    private int searchMaxValue(List<? extends Number> list){
        if(list == null)
            throw new NullPointerException
                    ("Список, в котором производится поиск максимума не создан");
        if(list.size() == 0)
            throw new IndexOutOfBoundsException
                    ("Список, в котором производится поиск максимума не заполнен");
        int max = 0;
        for(Number el: list){
            int e = (Integer) el;
            if(e > max)
                max = e;
        }
        return max;
    }

    /**
     * draw chart space
     * set the zoom range
     * and other parameters for correct display
     */
    private void paintChart() {
        addSeries();

        plot.setRangeBoundaries(0, MIN_VALUE_X, BoundaryMode.AUTO);
        plot.setDomainBoundaries(0, MAX_VALUE_Y, BoundaryMode.AUTO);
        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);

        plot.getGraph().setPaddingRight(2);

        //X-axis step drawing
        plot.setDomainStep(StepMode.SUBDIVIDE, 15);

        //axis name
        plot.setDomainLabel(NAME_AXIS_X);
        plot.setRangeLabel(NAME_AXIS_Y);

//        axis iteration format
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).
                setFormat(new DecimalFormat("0"));

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new Format() {
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


        //zoom setting
        PanZoom panZoom = PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.OUTER);
        plot.getRegistry().setEstimator(new ZoomEstimator());
        //set graph boundaries
        plot.getOuterLimits().set(MIN_VALUE_X, MAX_VALUE_X, MIN_VALUE_Y, MAX_VALUE_Y);
    }

    /**
     * plot the curve itself
     *set data for its construction,
     * define curve style
     */
    private void addSeries() {
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(Color.rgb(0, 0, 250),
                        Color.rgb(0, 0, 250),
                        Color.CYAN,
                        null);

        series1Format.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
        series1Format.getLinePaint().setStrokeWidth(PixelUtils.dpToPix(5));

        //design gradient fill for the chart
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient
                (0, 0, 0, 400, Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR));

        series1Format.setFillPaint(lineFill);

        SimpleXYSeries series = new SimpleXYSeries(valueTime,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, MONTH_TITLE_SERIAS);



        plot.addSeries(series, series1Format);
    }

    public void setFormat(String dateFormat){
        this.dateFormat = new SimpleDateFormat(dateFormat);
    }

}
