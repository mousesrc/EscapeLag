package com.amd.sensemi.optimize;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import com.amd.sensemi.SenseMI;
import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.config.Optimize;

import lombok.val;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class OverLoadMemoryRestart implements Runnable {
    private int restartTaskId = -1;
    
    @Override
    public void run() {
        if (Optimize.OverLoadMemoryRestartenable && isMemoryOverload() && restartTaskId == -1) {
            AzureAPI.bc(Optimize.OverLoadMemoryRestartWarnMessage);
            val bsc = Bukkit.getServer().getScheduler();

            restartTaskId = bsc.runTaskLater(SenseMI.instance, new Runnable() {
                @Override
                public void run() {
                    AzureAPI.tryRestartServer();
                }
            }, AzureAPI.toTicks(TimeUnit.SECONDS, Optimize.OverLoadMemoryRestartDelayTime)).getTaskId();
            
            if (!Optimize.OverLoadMemoryRestartCanCancel) return;
            bsc.runTaskTimer(SenseMI.instance, new Runnable() {
                @Override
                public void run() {
                    if (!isMemoryOverload()) {
                        bsc.cancelTask(restartTaskId);
                        restartTaskId = -1;
                    }
                }
            }, 20L, 40L);
        }
    }
    
    public static boolean isMemoryOverload() {
        val run = Runtime.getRuntime();
        return run.totalMemory() - run.freeMemory() > run.maxMemory() / 100 * Optimize.OverLoadMemoryRestartPercent;
    }
    
}
