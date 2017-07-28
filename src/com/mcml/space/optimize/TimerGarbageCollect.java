package com.mcml.space.optimize;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;

import static com.mcml.space.config.ConfigOptimize.TimerGcMessage;
import static com.mcml.space.config.ConfigOptimize.timerGC;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class TimerGarbageCollect implements Runnable, PluginExtends {
    public static void init(JavaPlugin plugin) {
        long ticks = AzureAPI.toTicks(TimeUnit.SECONDS, ConfigOptimize.TimerGcPeriod);
        Bukkit.getScheduler().runTaskTimer(plugin, new TimerGarbageCollect(), ticks, ticks);
        AzureAPI.log("内存释放模块已启用");
    }

    @Override
    public void run() {
        if(!timerGC) return;

        long mark = System.nanoTime();
        long released = collectGarbage();
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - mark);

        if(!StringUtils.isBlank(TimerGcMessage)) {
            String m = StringUtils.replace(TimerGcMessage, "%gc_released_memory%", String.valueOf(released));
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
