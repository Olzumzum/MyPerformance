package com.example.myperformance.workCharts;

import java.util.List;


/**
 * интерфейс реализует передачу данных
 * о дне, за которое подсчитывалось время
 * о вычисленном значении времени.
 * Информация используется для построения графиков
 */
public interface ReturningDataChart {
    //возвращает списко дней, для которых подсчитывалось время
    List<Integer> getListDayOfWeek();
    //возвращает значение времени
    List<Integer> getListTimeValue();
}
