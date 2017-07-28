package com.mcml.space.function;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import static com.mcml.space.config.ConfigFunction.controlExplode;
import static com.mcml.space.config.ConfigFunction.explodeControlType;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class ExplosionController implements PluginExtends {
    public static void init(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ExplosionController.EntityDetector(), plugin);
        
        if (VersionLevel.isHigherEquals(Version.MINECRAFT_1_8_R2)) {
            Bukkit.getPluginManager().registerEvents(new ExplosionController.BlockDetector(), plugin);
        }
        
        AzureAPI.log("爆炸控制模块已启动");
    }
    
    // since 1.8.3
    public static class BlockDetector implements Listener {
        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onExplode(BlockExplodeEvent evt) {
            handleExplode(evt, evt.blockList());
        }
    }
    
    // since 1.4.6
    public static class EntityDetector implements Listener {
        
        
        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onExplode(EntityExplodeEvent evt) {
            handleExplode(evt, evt.blockList());
        }
    }
    
    private static void handleExplode(Cancellable evt, List<Block> blocks) {
        if (!controlExplode) return;
        
        if (explodeControlType.equalsIgnoreCase("NoBlockBreak")) {
            blocks.clear();
        }
        if (explodeControlType.equalsIgnoreCase("NoExplode")) {
            evt.setCancelled(true);
        }
    }
}
