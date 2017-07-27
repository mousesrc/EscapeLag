package com.amd.sensemi.patch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.amd.sensemi.config.Patch;

/**
 * @author jiongjionger
 */
public class AntiDupeDropItem implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent evt) {
        if(Patch.fixDupeDropItem){
            Player player = evt.getPlayer();
            if (player == null || !player.isOnline() || !player.isValid()) evt.setCancelled(true);
        }
    }
}
