package com.mcml.space.fix;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import static com.mcml.space.config.ConfigPatch.fixRPGItemInfItem;

/**
 * @author Vlvxingze
 */
public class RPGItemPatch implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickup(PlayerPickupItemEvent evt) {
        if (!fixRPGItemInfItem) return;
        
        if (evt.getRemaining() <= 0) {
            evt.getItem().remove();
        }
    }
    
}