package com.amd.sensemi.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author SotrForgotten
 */
public class Ticker {
    private static long currentTick = -1;
    
    public static void bind(JavaPlugin plugin) {
        assert currentTick == -1 && plugin != null;
        
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                currentTick++;
            }
        }, 0L, 1L);
    }
    
    public static long current() {
        return currentTick;
    }
}
