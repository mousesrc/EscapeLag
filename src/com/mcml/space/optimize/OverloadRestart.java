package com.mcml.space.optimize;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;

import lombok.val;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class OverloadRestart implements Runnable, PluginExtends {
    public static void init(JavaPlugin plugin) {
        long ticksMin = AzureAPI.toTicks(TimeUnit.MINUTES, 20);
        Bukkit.getScheduler().runTaskTimer(plugin, new OverloadRestart(), ticksMin, ticksMin);
        
        AzureAPI.log("超负荷重启模块已启动");
    }
    
    // TODO 这个类以后还可以添加更多重启检测 不要重命名
    private int restartTaskId = -1;
    
    @Override
    public void run() {
        if (ConfigOptimize.OverLoadMemoryRestartenable && isMemoryOverload() && restartTaskId == -1) {
            AzureAPI.bc(ConfigOptimize.OverLoadMemoryRestartWarnMessage);
            val bsc = Bukkit.getServer().getScheduler();

            restartTaskId = bsc.runTaskLater(VLagger.MainThis, new Runnable() {
                @Override
                public void run() {
                    AzureAPI.tryRestartServer();
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
