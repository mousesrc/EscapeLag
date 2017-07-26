package com.mcml.space.optimize;


import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Utils;

import lombok.val;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class OverLoadMemoryRestart implements Runnable {
    private int restartTaskId = -1;
    
    @Override
    public void run() {
        if (ConfigOptimize.OverLoadMemoryRestartenable && isMemoryOverload() && restartTaskId == -1) {
            AzureAPI.bc(ConfigOptimize.OverLoadMemoryRestartWarnMessage);
            val bsc = Bukkit.getServer().getScheduler();

            restartTaskId = bsc.runTaskLater(VLagger.MainThis, new Runnable() {
                @Override
                public void run() {
                    Utils.RestartServer();
                }
            }, AzureAPI.toTicks(TimeUnit.SECONDS, ConfigOptimize.OverLoadMemoryRestartDelayTime)).getTaskId();
            
            if (!ConfigOptimize.OverLoadMemoryRestartCanCancel) return;
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
        return run.totalMemory() - run.freeMemory() > run.maxMemory() / 100 * ConfigOptimize.OverLoadMemoryRestartPercent;
    }
    
}
