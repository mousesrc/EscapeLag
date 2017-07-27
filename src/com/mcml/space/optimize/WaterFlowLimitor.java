package com.mcml.space.optimize;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.mcml.space.config.ConfigOptimize;

public class WaterFlowLimitor
        implements Listener {
	
	private final static HashMap<Chunk, Long> ChunkLastTime = new HashMap<Chunk, Long>();
	private final static HashMap<Chunk, Integer> CheckedTimes = new HashMap<Chunk, Integer>();

	@EventHandler
    public void WaterFowLimitor(BlockFromToEvent event) {
		if(ConfigOptimize.WaterFlowLimitorenable == true){
			Block block = event.getBlock();
			Chunk chunk = block.getChunk();
	        if (block.getType() == Material.STATIONARY_WATER || block.getType() == Material.STATIONARY_LAVA) {
	            if(CheckFast(block.getChunk())){
	            	Bukkit.broadcastMessage("触发了一次流水检查")
	            	if(CheckedTimes.get(chunk) == null){
	        			CheckedTimes.put(chunk, 0);
	        		}
	        		CheckedTimes.put(chunk, CheckedTimes.get(chunk) + 1);
	        		if(CheckedTimes.get(chunk) > ConfigOptimize.WaterFlowLimitorPerChunkTimes){
	        			event.setCancelled(true);
	        		}
	            }else{
	                ChunkLastTime.put(block.getChunk(), System.currentTimeMillis());
	            }
	        }
		}
    }
	
	private static boolean CheckFast(Chunk chunk) {
        if (ChunkLastTime.containsKey(chunk)) {
            return (((Long) ChunkLastTime.get(chunk)).longValue() + 50L > System.currentTimeMillis());
        }
        return false;
    }
}

