package com.example.myperformance.presenters;

import com.example.myperformance.data.model.TimePerform;

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
    private List<E> valueTime = new ArrayList<>();


    /**
     * writing date and time data to lists for display in a graph
     */
    private void timeAndDateRecording(List<E> listValue) {

        for (TimePerform el : (List<TimePerform>) listValue) {
            //получаем дату - ключ
            Calendar infoDate = new GregorianCalendar();
            infoDate.setTimeInMillis(el.getDatePerform());
            //записываем в список
            valueDate.add((E) infoDate.getTime());
            //записываем в список данные о времени
            valueTime.add((E) Integer.valueOf(el.getTimePerf()));
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
    public List<E> getListTimeValue() {
        return valueTime;
    }

    @Override
    public void setList(List list) {
        timeAndDateRecording(list);
    }


}
