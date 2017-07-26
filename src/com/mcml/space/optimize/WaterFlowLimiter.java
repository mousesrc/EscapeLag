package com.mcml.space.optimize;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import com.mcml.space.config.ConfigOptimize;

import lombok.val;

public class WaterFlowLimiter implements Listener {

    private final static HashMap<Chunk, Long> ChunkChecked = new HashMap<Chunk, Long>();
    @EventHandler(priority = EventPriority.LOWEST)
    public void WaterFowLimitor(BlockFromToEvent evt) {
        if(ConfigOptimize.WaterFlowLimitorenable){
            val block = evt.getBlock();
            val type = block.getType();
            
            if (type == Material.STATIONARY_WATER || type == Material.STATIONARY_LAVA) {
                if(CheckFast(block.getChunk())){
                    evt.setCancelled(true);
                }else{
                    ChunkChecked.put(block.getChunk(), System.currentTimeMillis());
                }
            }
        }
    }

    private static boolean CheckFast(Chunk chunk) {
        if (ChunkChecked.containsKey(chunk)) {
            return (ChunkChecked.get(chunk).longValue() + ConfigOptimize.WaterFlowLimitorPeriod > System.currentTimeMillis());
        }
        return false;
    }
}
