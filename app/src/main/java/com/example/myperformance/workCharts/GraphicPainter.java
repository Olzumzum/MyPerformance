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
import java.util.List;

public class GraphicPainter {
    private final String NAME_AXIS_X = "Дата";
    private final String NAME_AXIS_Y = "Время";
    private static final String MONTH_TITLE_SERIAS = "Производительность";

    private int MIN_VALUE_X = 0;
    private int MAX_VALUE_X;
    private int MIN_VALUE_Y = 0;
    private int MAX_VALUE_Y = 0;

    private XYPlot plot;
    private SimpleXYSeries series;
    private List<? extends Number> keyDate = new ArrayList<>();
    private List<? extends Number> valueTime = new ArrayList<>();

    /**
     * вызвать функции по отрисовке графика
     * @param plot - передать пространство для отрисовки графика
     * @param domainData - диапазон данных по оси Х
     * @param rangeData - диапазон данных по оси Y
     */
    public void paint(XYPlot plot, List<? extends Number> domainData, List<? extends Number> rangeData) {
        this.plot = plot;
        setDataChart(domainData, rangeData);
        paintChart();
    }

    /**
     * установить данные, необходимые для построения графика
     * @param keyDate - данные по оси Х
     * @param valueTime - данные по оси Y
     */
    private void setDataChart(List<? extends Number> keyDate, List<? extends Number> valueTime) {
        this.keyDate = keyDate;
        this.valueTime = valueTime;

        //рассчет диапазона графика (минимального и максимального значения)
        //для масштабирования
        MAX_VALUE_X = keyDate.size() -1;
        MAX_VALUE_Y = searchMaxValue(valueTime);
    }

    @org.jetbrains.annotations.Contract("null -> fail")
    private int searchMaxValue(List<? extends Number> list){
        if(list == null)
            throw new NullPointerException("Список, в котором производится поиск максимума не создан");
        if(list.size() == 0)
            throw new IndexOutOfBoundsException("Список, в котором производится поиск максимума не заполнен");
        int max = 0;
        for(Number el: list){
            int e = (Integer) el;
            if(e > max)
                max = e;
        }
        return max;
    }

    /**
     * отрисовать пространство графика
     * установить диапазон масштабирования
     * и другие параметры для корректного отображения
     */
    private void paintChart() {
        addSeries();

        plot.setRangeBoundaries(0, 10, BoundaryMode.AUTO);
        plot.setDomainBoundaries(0, 7, BoundaryMode.FIXED);
        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null);

        plot.getGraph().setPaddingRight(2);

        // отрисовка шага по оси Х
        plot.setDomainStep(StepMode.SUBDIVIDE, 9);

        // название осей
        plot.setDomainLabel(NAME_AXIS_X);
        plot.setRangeLabel(NAME_AXIS_Y);

        // формат итераций по осям
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).
                setFormat(new DecimalFormat("0"));

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
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
        PanZoom panZoom = PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.OUTER);
        plot.getRegistry().setEstimator(new ZoomEstimator());
        //задать границы графика
        plot.getOuterLimits().set(MIN_VALUE_X, MAX_VALUE_X, MIN_VALUE_Y, MAX_VALUE_Y);
    }

    /**
     * построить саму кривую графика
     * задать данные для ее построения,
     * определить стиль кривой
     */
    public void addSeries() {
        series = new SimpleXYSeries(valueTime,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, MONTH_TITLE_SERIAS);

        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(Color.rgb(0, 200, 0),
                        Color.rgb(0, 100, 0),
                        Color.CYAN,
                        null);

        series1Format.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
        series1Format.getLinePaint().setStrokeWidth(PixelUtils.dpToPix(5));

        //оформление градиентной заливки для графика
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient
                (0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));

        series1Format.setFillPaint(lineFill);

        plot.addSeries(series, series1Format);
    }

}
