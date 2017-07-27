package com.mcml.space.function;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mcml.space.util.AzureAPI;

import lombok.val;

import static com.mcml.space.config.Functions.preventSpawnerModify;
import static com.mcml.space.config.Functions.messagePreventSpawnerModify;

/**
 * @author Vlvxingze
 */
public class SpawnerController implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void NoChangeLimit(PlayerInteractEvent evt) {
        if (!preventSpawnerModify || evt.getItem() == null || evt.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        val player = evt.getPlayer();
        if (AzureAPI.hasPerm(evt.getPlayer(), "VLagger.admin")) {
            return;
        }
        
        if (evt.getClickedBlock().getType() == Material.MOB_SPAWNER) {
            val type = evt.getItem().getType();
            if (type == Material.MONSTER_EGG || type == Material.MONSTER_EGGS) {
                evt.setCancelled(true);
                AzureAPI.log(player, messagePreventSpawnerModify);
            }
        }
    }
    
}