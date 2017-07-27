package com.amd.sensemi.function;

import static com.amd.sensemi.config.Function.controlExplode;
import static com.amd.sensemi.config.Function.explodeControlType;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class ExplosionController {
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
