package com.mcml.space.patch;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import lombok.val;

public class BonemealDupePatch implements Listener, PluginExtends {
    public static void init(JavaPlugin plugin) {
        if (VersionLevel.isHigherThan(Version.MINECRAFT_1_7_R4)) return;
        
        Bukkit.getPluginManager().registerEvents(new BonemealDupePatch(), plugin);
        AzureAPI.log("骨粉修复模块已启用");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void auth(final BlockBreakEvent evt) {
        val prevType = evt.getBlock().getType();
        
        Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
            @Override
            public void run() {
                if (evt.isCancelled()) return;
                
                if (prevType == evt.getBlock().getType()) {
                    AzureAPI.log(evt.getPlayer(), "骨粉bug检测ok");
                }
            }
        }, 1L);
    }
    
}