package com.mcml.space.optimize;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;

import lombok.val;

import static com.mcml.space.config.ConfigOptimize.overloadMemoryRestart;
import static com.mcml.space.config.ConfigOptimize.messageOverloadMemory;
import static com.mcml.space.config.ConfigOptimize.overloadMemoryRestartDelay;
import static com.mcml.space.config.ConfigOptimize.overloadMemoryPercent;
import static com.mcml.space.config.ConfigOptimize.overloadMemoryCancellable;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class OverloadAction implements Runnable {
    private int restartTaskId = -1;
    
    @Override
    public void run() {
        if (overloadMemoryRestart && isMemoryOverload() && restartTaskId == -1) {
            AzureAPI.bc(messageOverloadMemory);
            val bsc = Bukkit.getServer().getScheduler();

            restartTaskId = bsc.runTaskLater(VLagger.MainThis, new Runnable() {
                @Override
                public void run() {
                    Bukkit.shutdown();
                }
            }, AzureAPI.toTicks(TimeUnit.SECONDS, overloadMemoryRestartDelay)).getTaskId();
            
            if (!overloadMemoryCancellable) return;
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
        val max = run.maxMemory();
        return max - run.totalMemory() > max / 100 * overloadMemoryPercent;
    }
    
}
