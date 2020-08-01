package com.example.myperformance.presenters;

import com.example.myperformance.data.model.CriterionChart;
import com.example.myperformance.data.model.TimePerform;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Prepares data for presentation on a chart
 *
 * @param <E>
 */
public class ChartDataHolder<E> implements ReturningDataChart<E> {

    //list of dates on which the count was made
    private List<E> valueDate = new ArrayList<>();
    //time count list
    private List<E> valueTime = new ArrayList<>();
    private CriterionChart criterionChart;

    public ChartDataHolder(CriterionChart criterionChart) {
        this.criterionChart = criterionChart;
    }


    /**
     * writing date and time data to lists for display in a graph
     */
    private void timeAndDateRecording(List<E> listValue) {
        if (criterionChart != CriterionChart.TODAY)
            listValue = summatorTime((List<TimePerform>) listValue);

        for (TimePerform el : (List<TimePerform>) listValue) {
            //get date - key
            Calendar infoDate = new GregorianCalendar();
            infoDate.setTimeInMillis(el.getDatePerform());
            //put on the list
            valueDate.add((E) infoDate.getTime());
            //write time data to the list
            valueTime.add((E) Integer.valueOf(el.getTimePerf()));
        }
    }

    private List<E> summatorTime(List<TimePerform> listValue) {
        if (listValue == null)
            throw new NullPointerException("list is null");
        if (listValue.isEmpty())
            throw new IllegalArgumentException("list is empty");

        List<TimePerform> resultList = new ArrayList<>();

        for (int i = 0; i < listValue.size(); i++) {
            long date = listValue.get(i).getDatePerform();
            //if the entry with this date is not recorded - do
            if (checkSimularDate(date, resultList)) {
                int timeValue = listValue.get(i).getTimePerf();
                //all items after current item
                for (int j = i + 1; j < listValue.size(); j++) {
                    //if date is same
                    if (date == listValue.get(j).getDatePerform()) {
                        timeValue += listValue.get(j).getTimePerf();
                    }
                }
                resultList.add(new TimePerform(date, timeValue));
            }
        }

        return (List<E>) resultList;
    }

    private boolean checkSimularDate(long date, List<TimePerform> list) {
        if (list.isEmpty())
            return true;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDatePerform() == date)
                return false;
        }
        return true;
    }

    /**
     * @return list of days
     */
    @Override
    public List<E> getListDayOfWeek() {
        return valueDate;
    }

    /**
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
