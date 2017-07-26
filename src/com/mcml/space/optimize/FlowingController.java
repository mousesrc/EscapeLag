package com.mcml.space.optimize;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Ticker;

import lombok.val;

public class FlowingController implements Listener {
    private static long lastTick;
    private static long currentFlowing;
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onFlow(final BlockFromToEvent evt) {
        if(!ConfigOptimize.WaterFlowLimitorenable) return;
        long now = Ticker.current();
        if (lastTick != now) {
            currentFlowing = 0;
            lastTick = now;
        }
        currentFlowing++;
        
        AzureAPI.log("currentFlowing: " + currentFlowing);
        AzureAPI.log("currentTick: " + now);
        
        val block = evt.getBlock();
        val type = block.getType();
        
        if (type == Material.STATIONARY_WATER || type == Material.STATIONARY_LAVA || type == Material.WATER || type == Material.LAVA) {
            AzureAPI.log("we detected a flow action");
            if (currentFlowing > ConfigOptimize.flowingMaxConcurrent) {
                AzureAPI.log("we delayed a flow task");
                evt.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {
                        AzureAPI.log("a delayed task fired");
                        val to = evt.getToBlock();
                        val event = new BlockFromToEvent(block, to);
                        Bukkit.getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            to.setTypeIdAndData(to.getTypeId(), to.getData(), true);
                        }
                    }
                }, 1L);
            }
            return;
        }
        
        AzureAPI.log("we passed a flow task");
    }
    
}
