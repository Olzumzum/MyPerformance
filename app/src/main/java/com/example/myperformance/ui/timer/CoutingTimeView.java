package com.example.myperformance.ui.timer;

import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * provides an action timer
 */
public class CoutingTimeView {
    //action time counter
    private Chronometer chronometerEmployment;
    //flag indicating whether the timer has been started
    private boolean running = false;
    //stores the amount of time elapsed since the timer started
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

    public CoutingTimeView(Chronometer chronometerEmployment) {
        this.chronometerEmployment = chronometerEmployment;
    }

    /**
     * start timer
     */
    public void startCount() {
        if (!running) {
            chronometerEmployment.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometerEmployment.start();
            running = true;
        }
    }

    /**
     * pause the timer
     */
    public void pauseCounting() {
        if (running) {
            chronometerEmployment.stop();
            setCurrentTime();
            running = false;
        }
    }

    /**
     * stop timer
     */
    public void stopCounting() {
        chronometerEmployment.stop();
        running = false;
        pauseOffset = 0;
        chronometerEmployment.setBase(SystemClock.elapsedRealtime());
    }

    /**
     * restarts the timer when onPause
     */
    public void restartChronometr() {
        if (pauseOffset != 0)
            startCount();
    }

    /**
     * calculates how much time has passed since the start of the timer
     */
    public void setCurrentTime() {
        if (running)
            pauseOffset = SystemClock.elapsedRealtime() - chronometerEmployment.getBase();
    }
}
