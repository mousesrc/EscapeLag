package com.mcml.space.optimize;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import com.mcml.space.config.Optimizes;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;

import lombok.val;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class OverLoadMemoryRestart implements Runnable {
    private int restartTaskId = -1;
    
    @Override
    public void run() {
        if (Optimizes.OverLoadMemoryRestartenable && isMemoryOverload() && restartTaskId == -1) {
            AzureAPI.bc(Optimizes.OverLoadMemoryRestartWarnMessage);
            val bsc = Bukkit.getServer().getScheduler();

            restartTaskId = bsc.runTaskLater(VLagger.MainThis, new Runnable() {
                @Override
                public void run() {
                    AzureAPI.tryRestartServer();
                }
            }, AzureAPI.toTicks(TimeUnit.SECONDS, Optimizes.OverLoadMemoryRestartDelayTime)).getTaskId();
            
            if (!Optimizes.OverLoadMemoryRestartCanCancel) return;
            bsc.runTaskTimer(VLagger.MainThis, new Runnable() {
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
        return run.totalMemory() - run.freeMemory() > run.maxMemory() / 100 * Optimizes.OverLoadMemoryRestartPercent;
    }
    
}
