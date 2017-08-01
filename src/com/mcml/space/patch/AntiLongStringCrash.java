package com.mcml.space.patch;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;

public class AntiLongStringCrash implements Listener{

    @SuppressWarnings("deprecation")
    @EventHandler
    public void InteractCheck(PlayerInteractEvent evt){
        if(ConfigPatch.AntiLongStringCrashenable == true){
            ItemStack item = evt.getItem();
            Player player = evt.getPlayer();
            if(item != null){
                if(item.hasItemMeta() && item.getItemMeta().getDisplayName() != null){
                    if(item.getItemMeta().getDisplayName().length() >= 127){
                        evt.setCancelled(true);
                        player.setItemInHand(null);
                        AzureAPI.log(player, ConfigPatch.AntiLongStringCrashWarnMessage);
                    }
                }
            }
        }
    }

    @EventHandler
    public void ClickCheckItem(InventoryClickEvent evt){
        if(ConfigPatch.AntiLongStringCrashenable == true){
            if(evt.getWhoClicked().getType() != EntityType.PLAYER){
                return;
            }
            Player player = (Player) evt.getWhoClicked();
            ItemStack item = evt.getCursor();
            if(item != null){
                if(item.hasItemMeta() && item.getItemMeta().getDisplayName() != null){
                    if(item.getItemMeta().getDisplayName().length() >= 127){
                        evt.setCancelled(true);
                        evt.setCurrentItem(null);
                        AzureAPI.log(player, ConfigPatch.AntiLongStringCrashWarnMessage);
                    }
                }
            }
        }
    }
}
