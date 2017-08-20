package com.mcml.space.optimize;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.EscapeLag;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;

import lombok.val;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class OverloadRestart implements Runnable, PluginExtends {
    public static void init(JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, new OverloadRestart(), 7 * 20, 7 * 20);
        
        AzureAPI.log("超负荷重启模块已启动");
    }
    
    // TODO 这个类以后还可以添加更多重启检测 不要重命名
    
    @Override
    public void run() {
        if (ConfigOptimize.OverLoadMemoryRestartenable && isMemoryOverload()) {
            AzureAPI.bc(ConfigOptimize.OverLoadMemoryRestartWarnMessage);
            Bukkit.getServer().getScheduler().runTaskLater(EscapeLag.MainThis, new Runnable() {
                @Override
                public void run() {
                    AzureAPI.tryRestartServer();
                }
            }, ConfigOptimize.OverLoadMemoryRestartDelayTime * 20);
        }
    }
    
    public static boolean isMemoryOverload() {
        val run = Runtime.getRuntime();
        return run.totalMemory() - run.freeMemory() > run.maxMemory() / 100 * ConfigOptimize.OverLoadMemoryRestartPercent;
    }
    
}
