package com.mcml.space.patch;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.mcml.space.config.ConfigPatch;

public class AntiInfRail implements Listener {
	
	private long LastCheckedTime = System.currentTimeMillis();

    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PhysicsCheck(BlockPhysicsEvent event) {
        if (ConfigPatch.fixInfRail == true) {
            if (event.getChangedType().name().contains("RAIL")) {
                if(CheckFast()){
                	event.setCancelled(true);
                }
                LastCheckedTime = System.currentTimeMillis();
            }
            if (event.getChangedTypeId() == 165) {
            	if(CheckFast()){
                	event.setCancelled(true);
                }
                LastCheckedTime = System.currentTimeMillis();
            }
        }
    }
    
    public boolean CheckFast(){
    	return LastCheckedTime + 10L > System.currentTimeMillis();
    }
}
// TODO confirm details
