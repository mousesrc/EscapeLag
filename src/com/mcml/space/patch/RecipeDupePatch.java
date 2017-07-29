package com.mcml.space.patch;

import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import com.comphenix.protocol.PacketType.Play;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Sets;
import com.mcml.space.config.ConfigPatch;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.PluginExtends;

public class RecipeDupePatch implements Listener, PluginExtends {
    public final Set<Player> keepers;
    
    public RecipeDupePatch() {
        keepers = Sets.newHashSet();
        
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(VLagger.MainThis, Play.Client.AUTO_RECIPE) {
            @Override
            public void onPacketReceiving(PacketEvent evt) {
                if (!ConfigPatch.autoRecipePatch) return;
                keepers.add(evt.getPlayer());
            }
        });
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(PlayerPickupItemEvent evt) {
        if (!ConfigPatch.autoRecipePatch) return;
        if (keepers.contains(evt.getPlayer())) evt.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(InventoryCloseEvent evt) {
        if (!ConfigPatch.autoRecipePatch) return;
        keepers.remove(evt.getPlayer());
    }
    
}