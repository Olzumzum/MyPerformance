package com.example.myperformance.workCharts;

import android.util.Log;

import com.example.myperformance.model.TimePerforme;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Prepares data for presentation on a chart
 * @param <E>
 */
public class ChartDataHolder <E> implements ReturningDataChart {

    //list of dates on which the count was made
    private List<E> valueDate = new ArrayList<>();
    //time count list
    private List<Integer> valueTime = new ArrayList<>();

    public ChartDataHolder(List<E> list) {
        timeAndDateRecording(list);
    }

    /**
     * writing date and time data to lists for display in a graph
     */
    private void timeAndDateRecording(List<E> listValue) {

        for (TimePerforme el : (List<TimePerforme>) listValue) {
            //получаем дату - ключ
            Calendar infoDate = new GregorianCalendar();
            infoDate.setTimeInMillis(el.getDatePerfor());
            //записываем в список

            Log.d("DaysMap", "день " + infoDate.get(Calendar.DAY_OF_MONTH) + "."
                    + infoDate.get(Calendar.MONTH) + " время " + el.getTimePerf());


            valueDate.add((E) infoDate.getTime());
            //записываем в список данные о времени
            valueTime.add(el.getTimePerf());
        }
    }

    /**
     * @return list of days
     */
    @Override
    public  List<?> getListDayOfWeek() {
        return valueDate;
    }

    /**
     *
     * @return time value list
     */
    @Override
    public List<Integer> getListTimeValue() {
        return valueTime;
    }


}
