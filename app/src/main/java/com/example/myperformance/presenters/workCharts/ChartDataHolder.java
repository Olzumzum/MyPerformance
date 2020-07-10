package com.example.myperformance.presenters.workCharts;

import android.util.Log;

import com.example.myperformance.model.TimePerform;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Prepares data for presentation on a chart
 * @param <E>
 */
public class ChartDataHolder <E> implements ReturningDataChart<E> {

    //list of dates on which the count was made
    private List<E> valueDate = new ArrayList<>();
    //time count list
    private List<Integer> valueTime = new ArrayList<>();


    /**
     * writing date and time data to lists for display in a graph
     */
    private void timeAndDateRecording(List<E> listValue) {

        for (TimePerform el : (List<TimePerform>) listValue) {
            //получаем дату - ключ
            Calendar infoDate = new GregorianCalendar();
            infoDate.setTimeInMillis(el.getDatePerform());
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
    public  List<E> getListDayOfWeek() {
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

    @Override
    public void setList(List list) {
        timeAndDateRecording(list);
    }


}
