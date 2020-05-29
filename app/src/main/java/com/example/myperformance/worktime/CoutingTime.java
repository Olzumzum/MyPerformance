package com.example.myperformance.worktime;

import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * обеспечивает работу таймера выполнения действия
 */
public class CoutingTime {
    //счетчик времени выполнения действия
    private Chronometer chronometerEmployment;
    //флаг, обозначающий, был ли запущен таймер
    private boolean running = false;
    //хранит количество времени, прошедшее с запуска таймера
    private long pauseOffset;

    public long getPauseOffset() {
        return pauseOffset;
    }

    public boolean getRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPauseOffset(long pauseOffset) {
        this.pauseOffset = pauseOffset;
    }

    public CoutingTime(Chronometer chronometerEmployment) {
        this.chronometerEmployment = chronometerEmployment;
    }

    /**
     * запустить таймер
     */
    public void startCount() {
        if (!running) {
            chronometerEmployment.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometerEmployment.start();
            running = true;
        }
    }

    /**
     * поставить таймер на паузу
     */
    public void pauseCounting() {
        if (running) {
            chronometerEmployment.stop();
            setCurrentTime();
            running = false;
        }
    }

    /**
     * остановить таймер
     */
    public void stopCounting() {
        chronometerEmployment.stop();
        running = false;
        pauseOffset = 0;
        chronometerEmployment.setBase(SystemClock.elapsedRealtime());
    }

    /**
     * перезапускает таймер при onPause
     */
    public void restartChronometr() {
        if (pauseOffset != 0)
            startCount();
    }

    /**
     * рассчитывает сколько прошшло времени с начала запуска таймера
     */
    public void setCurrentTime() {
        if (running)
            pauseOffset = SystemClock.elapsedRealtime() - chronometerEmployment.getBase();
    }
}
