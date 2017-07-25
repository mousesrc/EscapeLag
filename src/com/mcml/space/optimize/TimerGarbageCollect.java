package com.mcml.space.optimize;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.mcml.space.util.AzureAPI;

import static com.mcml.space.config.ConfigOptimize.messageGC;
import static com.mcml.space.config.ConfigOptimize.timerGC;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class TimerGarbageCollect implements Runnable {

    @Override
    public void run() {
        if(!timerGC) return;

        long mark = System.nanoTime();
        long released = collectGarbage();
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - mark);

        if(!StringUtils.isBlank(messageGC)) {
            String m = StringUtils.replace(messageGC, "%gc_released_memory%", String.valueOf(released));
            m = StringUtils.replace(m, "%gc_cost_time%", String.valueOf(duration) + "ms");
            AzureAPI.log(m);
        }
    }

    public static long collectGarbage() {
        long before = Runtime.getRuntime().totalMemory();
        System.gc();
        return (before - Runtime.getRuntime().totalMemory()) / 1024 / 1024; // to mb
    }
}
