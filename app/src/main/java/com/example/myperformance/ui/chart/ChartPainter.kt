package com.example.myperformance.ui.chart

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import com.androidplot.Plot
import com.androidplot.util.PixelUtils
import com.androidplot.xy.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ChartPainter {

    private val NAME_AXIS_X = "Дата"
    private val NAME_AXIS_Y = "Время"
    private val MONTH_TITLE_SERIAS = "Производительность"

    private var MIN_VALUE_X = 0
    private var MAX_VALUE_X = 0
    private var MIN_VALUE_Y = 0
    private var MAX_VALUE_Y = 0

    private lateinit var plot: XYPlot
    private lateinit var series: SimpleXYSeries
    private lateinit var series1Format: LineAndPointFormatter
    private lateinit var dataFormat: SimpleDateFormat

    private lateinit var keyDate: List<Number>
    private lateinit var valueTime: List<Number>
    private lateinit var panZoom: PanZoom

    constructor(){
        GlobalScope.launch(Dispatchers.IO){
            seriesFormat()
        }
    }

    fun paint(plot: XYPlot, domainData: List<Number>, rangeData: List<Number> ){
        addSeries()

//        this.keyDate = keyDate
//        this.valueTime = valueTime

        MAX_VALUE_X = 10
        MAX_VALUE_Y = 50

        GlobalScope.launch(Dispatchers.IO){
            paintChart()
        }
    }



    suspend fun paintChart(){
        addSeries()

        plot.setRangeBoundaries(0, MIN_VALUE_X, BoundaryMode.FIXED)
        plot.setDomainBoundaries(0, MAX_VALUE_Y, BoundaryMode.FIXED)
        plot.setBorderStyle(Plot.BorderStyle.NONE, null, null)

        plot.graph.paddingRight = 2f

        //X-axis step drawing
        plot.setDomainStep(StepMode.SUBDIVIDE, 9.0)


        //axis name
        plot.setDomainLabel(NAME_AXIS_X)
        plot.setRangeLabel(NAME_AXIS_Y)

        panZoom = PanZoom.attach(plot, PanZoom.Pan.BOTH, PanZoom.Zoom.STRETCH_BOTH, PanZoom.ZoomLimit.OUTER)
        plot.registry.estimator = ZoomEstimator()
        //set graph boundaries
        plot.outerLimits[MIN_VALUE_X, MAX_VALUE_X, MIN_VALUE_Y] = MAX_VALUE_Y



    }

    fun addSeries(){
        series = SimpleXYSeries(valueTime,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                MONTH_TITLE_SERIAS
        )
    }

    fun setFormat(dataFormat: String){
        this.dataFormat = SimpleDateFormat(dataFormat)
    }

    suspend fun seriesFormat(){
        series1Format = LineAndPointFormatter(Color.rgb(0, 0, 250),
                Color.rgb(0, 0, 250),
                Color.CYAN,
                null)

        series1Format.vertexPaint.strokeWidth = PixelUtils.dpToPix(10f)
        series1Format.linePaint.strokeWidth = PixelUtils.dpToPix(5f)

        //design gradient fill for the chart

        //design gradient fill for the chart
        val lineFill = Paint()
        lineFill.alpha = 200
        lineFill.shader = LinearGradient(
                0.toFloat(), 0.toFloat(), 0.toFloat(),
                400.toFloat(), Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR)

        series1Format.fillPaint = lineFill
    }
}