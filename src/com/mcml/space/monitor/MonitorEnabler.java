package com.mcml.space.monitor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mcml.space.config.ConfigOptimize;

public class MonitorEnabler implements Listener{
	private boolean hasEnabled = false;
	
	@EventHandler
	public void JoinEnableMonitor(PlayerJoinEvent event){
		if(hasEnabled == false){
			if(ConfigOptimize.Monitorenable){
				MonitorUtils.enable();
			}
			hasEnabled = true;
		}
	}
}
