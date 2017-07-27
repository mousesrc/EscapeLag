package com.mcml.space.optimize;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.mcml.space.config.Settings;
import com.mcml.space.config.Optimizes;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;

public class AntiRedstone implements Listener {
	
	private HashMap<Location,Integer> CheckedTimes = new HashMap<Location, Integer>();

	public AntiRedstone(){
		Bukkit.getScheduler().runTaskTimer(VLagger.MainThis, new Runnable(){
			public void run(){
				CheckedTimes.clear();
			}
		}, 7 * 20, 7 * 20);
	}
	
	@EventHandler
	public void CheckRedstone(BlockRedstoneEvent event){
		if(event.getOldCurrent() > event.getNewCurrent()){
			return;
		}
		Block block = event.getBlock();
		Location loc = block.getLocation();
		if(CheckedTimes.get(loc) == null){
			CheckedTimes.put(loc, 0);
		}
		CheckedTimes.put(loc, CheckedTimes.get(loc) + 1);
		if(CheckedTimes.get(loc) > Optimizes.AntiRedstoneTimes){
			Bukkit.broadcastMessage(Optimizes.AntiRedstoneRemoveBlockList + "");
			if(Optimizes.AntiRedstoneRemoveBlockList.contains(block.getType().name())){
				block.setType(Material.AIR);
				String message = Optimizes.AntiRedstoneMessage;
				message = message.replaceAll("%location%", loc.toString());
				AzureAPI.bc(Settings.PluginPrefix, message);
			}
		}
	}
}
