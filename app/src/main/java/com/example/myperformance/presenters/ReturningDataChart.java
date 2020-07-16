package com.example.myperformance.presenters;

import java.util.List;


/**
 * interface implements data transfer
 * about the day for which time was calculated
 * about the calculated value of time.
 * Information is used to plot charts
 */
public interface ReturningDataChart <E>{
    //returns a list of days for which time was counted
    List<E> getListDayOfWeek();
    //returns time value
    List<Integer> getListTimeValue();
    //set all the data to separate them for display on the graph
    void setList(List<E> list);
}
