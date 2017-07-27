package com.amd.sensemi.optimize;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

import com.amd.sensemi.config.Optimize;

public class FireLimitor implements Listener {
    // TODO tied to tick
    private final static HashMap<Chunk, Long> ChunkChecked = new HashMap<Chunk, Long>();

    @EventHandler
    public void WhenFireLimitor(BlockIgniteEvent event) {
        if (Optimize.FireLimitorenable == true) {
            if (event.getCause() == IgniteCause.FLINT_AND_STEEL) {
                return;
            }
            Block block = event.getBlock();
            if (CheckFast(block.getChunk())) {
                event.setCancelled(true);
            } else {
                ChunkChecked.put(block.getChunk(), System.currentTimeMillis());
            }
        }
    }

    private static boolean CheckFast(Chunk chunk) {
        if (ChunkChecked.containsKey(chunk)) {
            return (ChunkChecked.get(chunk).longValue() + Optimize.FireLimitorPeriod > System
                    .currentTimeMillis());
        }
        return false;
    }
}
