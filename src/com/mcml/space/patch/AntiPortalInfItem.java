package com.mcml.space.patch;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;

public class AntiPortalInfItem implements Listener {

    @EventHandler
    public void PortalCheck(EntityPortalEvent event) {
        if (ConfigPatch.fixPortalInfItem) {
            if (event.getEntityType() == EntityType.MINECART_CHEST || event.getEntityType() == EntityType.MINECART_FURNACE || event.getEntityType() == EntityType.MINECART_HOPPER) {
                event.setCancelled(true);
                event.getEntity().remove();
                AzureAPI.bc(ConfigPatch.AntiPortalInfItemWarnMessage);
            }
        }
    }
}