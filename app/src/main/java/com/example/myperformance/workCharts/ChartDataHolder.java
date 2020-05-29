package com.example.myperformance.workCharts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartDataHolder implements ReturningDataChart {
    private Map<Calendar, Integer> timeHolderMap = new HashMap<>();
    private List<Integer> keyDate = new ArrayList<>();
    private List<Integer> valueTime = new ArrayList<>();

    public ChartDataHolder(){
        timeHolderMap.put(new GregorianCalendar(2006, 0, 15), 15);
        timeHolderMap.put(new GregorianCalendar(2006, 0, 16), 4);
        timeHolderMap.put(new GregorianCalendar(2006, 0, 17), 20);
        timeHolderMap.put(new GregorianCalendar(2006, 0, 18), 0);
        timeHolderMap.put(new GregorianCalendar(2006, 0, 19), 3);

        timeAndDateRecording();
    }

    //запись данных о датах и времени в списки для отображения в виде графика
    private void timeAndDateRecording(){
        for(Map.Entry entry : timeHolderMap.entrySet()){
            //получаем дату - ключ
            Calendar infoDate = (Calendar) entry.getKey();
            //записываем в список
            keyDate.add(infoDate.get(Calendar.DAY_OF_MONTH));
            //записываем в список данные о времени
            valueTime.add((Integer) entry.getValue());


//            Log.d("MyLog", "Текущая дата и колчество часов " +
//                    ((Calendar) entry.getKey()).get(Calendar.DAY_OF_MONTH) +
//                    " " +
//                    entry.getValue());
        }
    }

    @Override
    public List<Integer> getListDayOfWeek() {
        return keyDate;
    }

    @Override
    public List<Integer> getListTimeValue() {
        return valueTime;
    }
}
