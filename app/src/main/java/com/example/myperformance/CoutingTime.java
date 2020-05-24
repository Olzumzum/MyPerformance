package com.example.myperformance;

import android.os.SystemClock;
import android.widget.Chronometer;

class CoutingTime {
    //счетчик времени выполнения действия
    private Chronometer chronometerEmployment;
    private boolean running = false;
    private long pauseOffset;

    long getPauseOffset(){return pauseOffset;}

    boolean getRunning(){return running;}

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPauseOffset(long pauseOffset) {
        this.pauseOffset = pauseOffset;
    }

    public CoutingTime(Chronometer chronometerEmployment) {
        this.chronometerEmployment = chronometerEmployment;
    }

    public void startCount(){
        if(!running){
            chronometerEmployment.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometerEmployment.start();
            running = true;
        }
    }

    public void pauseCounting(){
        if(running){
            chronometerEmployment.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometerEmployment.getBase();
            running = false;
        }
    }

    public void stopCounting(){
        chronometerEmployment.stop();
        running = false;
        pauseOffset = 0;
        chronometerEmployment.setBase(SystemClock.elapsedRealtime());
    }
}
