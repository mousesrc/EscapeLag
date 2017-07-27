package com.amd.sensemi.patch;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.config.Patch;

public class AntiPortalInfItem implements Listener {

    @EventHandler
    public void PortalCheck(EntityPortalEvent event) {
        if (Patch.fixPortalInfItem) {
            if (event.getEntityType() == EntityType.MINECART_CHEST || event.getEntityType() == EntityType.MINECART_FURNACE || event.getEntityType() == EntityType.MINECART_HOPPER) {
                event.setCancelled(true);
                event.getEntity().remove();
                AzureAPI.bc(Patch.AntiPortalInfItemWarnMessage);
            }
        }
    }
}