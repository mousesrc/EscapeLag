package com.amd.sensemi.optimize;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.amd.sensemi.VLagger;
import com.amd.sensemi.config.Optimize;

public class WaterFlowLimitor
        implements Listener {
	
	private final static HashMap<Chunk, Long> ChunkLastTime = new HashMap<Chunk, Long>();
	private final static HashMap<Chunk, Integer> CheckedTimes = new HashMap<Chunk, Integer>();
	
	public WaterFlowLimitor(){
		Bukkit.getScheduler().runTaskTimer(VLagger.MainThis, new Runnable(){
			public void run(){
				CheckedTimes.clear();
			}
		}, 7 * 20, 7 * 20);
	}
	
	@EventHandler
    public void WaterFowLimitor(BlockFromToEvent event) {
		if(Optimize.WaterFlowLimitorenable == true){
			Block block = event.getBlock();
			Chunk chunk = block.getChunk();
	        if (block.getType() == Material.STATIONARY_WATER || block.getType() == Material.STATIONARY_LAVA) {
	            if(CheckFast(block.getChunk())){
	            	if(CheckedTimes.get(chunk) == null){
	        			CheckedTimes.put(chunk, 0);
	        		}
	        		CheckedTimes.put(chunk, CheckedTimes.get(chunk) + 1);
	        		if(CheckedTimes.get(chunk) > Optimize.WaterFlowLimitorPerChunkTimes){
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

