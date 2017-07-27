package com.amd.sensemi.patch;

import static com.amd.sensemi.config.Patch.fixRPGItemInfItem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

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