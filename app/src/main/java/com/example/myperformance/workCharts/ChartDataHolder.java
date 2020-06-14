package com.example.myperformance.workCharts;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myperformance.viewModel.TimePerformeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChartDataHolder <E> implements ReturningDataChart {
    private Map<Calendar, Integer> timeHolderMap = new TreeMap<>();
    private List<E> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();

    //viewModel retrieving stored data from a database
    private TimePerformeViewModel viewModel;

    public ChartDataHolder() {
//        timeHolderMap.put(new GregorianCalendar(2006, 5, 15), 15);
//        timeHolderMap.put(new GregorianCalendar(2006, 5, 16), 4);
//        timeHolderMap.put(new GregorianCalendar(2006, 5, 17), 20);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 18), 0);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 19), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 20), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 21), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 22), 5);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 23), 8);
//        timeHolderMap.put(new GregorianCalendar(2006, 11, 24), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 25), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 26), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 27), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 28), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 29), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 30), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 0, 31), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 1), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 2), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 3), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 4), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 5), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 6), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 7), 3);
//        timeHolderMap.put(new GregorianCalendar(2006, 1, 8), 3);

        viewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(TimePerformeViewModel.class);
//        viewModel.getAllTimePerforme().observe(this, Observer{timePerforme -> );
        timeAndDateRecording();
    }

    /**
     * запись данных о датах и времени в списки для отображения в виде графика
     */
    private void timeAndDateRecording() {
        for (Map.Entry entry : timeHolderMap.entrySet()) {
            //получаем дату - ключ
            Calendar infoDate = (Calendar) entry.getKey();
            //записываем в список

            Log.d("DaysMap", "день " + infoDate.get(Calendar.DAY_OF_MONTH) + "."
                    + infoDate.get(Calendar.MONTH) + " время " + entry.getValue());


            keyDate.add((E) infoDate.getTime());
            //записываем в список данные о времени
            valueTime.add((Integer) entry.getValue());


//            Log.d("MyLog", "Текущая дата и колчество часов " +
//                    ((Calendar) entry.getKey()).get(Calendar.DAY_OF_MONTH) +
//                    " " +
//                    entry.getValue());
        }
    }

    /**
     * @return вернуть список дней
     */
    @Override
    public  List<?> getListDayOfWeek() {
        return keyDate;
    }

    /**
     *
     * @return вернуть список значений времени
     */
    @Override
    public List<Integer> getListTimeValue() {
        return valueTime;
    }


}
