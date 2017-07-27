package com.mcml.space.fix;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.mcml.space.config.ConfigPatch;

/**
 * @author jiongjionger
 */
public class AntiDupeDropItem implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent evt) {
        if(ConfigPatch.fixDupeDropItem){
            Player player = evt.getPlayer();
            if (player == null || !player.isOnline() || !player.isValid()) evt.setCancelled(true);
        }
    }
}
