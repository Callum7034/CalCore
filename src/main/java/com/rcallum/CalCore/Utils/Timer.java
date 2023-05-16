package com.rcallum.CalCore.Utils;

public class Timer {
    private long startTime;
    private long finishTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        finishTime = System.currentTimeMillis();
    }

    public long loadUpTimeMillis() {
        return finishTime - startTime;
    }
}
