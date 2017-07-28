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
	public void InteractCheck(PlayerInteractEvent event){
		if(ConfigPatch.AntiLongStringCrashenable == true){
			ItemStack item = event.getItem();
			Player player = event.getPlayer();
			if(item != null){
				if(item.hasItemMeta() && item.getItemMeta().getDisplayName() != null){
					if(item.getItemMeta().getDisplayName().length() > 20){
						player.setItemInHand(null);
						AzureAPI.log(player, ConfigPatch.AntiLongStringCrashWarnMessage);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void ClickCheckItem(InventoryClickEvent event){
		if(ConfigPatch.AntiLongStringCrashenable == true){
			if(event.getWhoClicked().getType() != EntityType.PLAYER){
				return;
			}
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCursor();
			if(item != null){
				if(item.hasItemMeta() && item.getItemMeta().getDisplayName() != null){
					if(item.getItemMeta().getDisplayName().length() > 20){
						player.setItemInHand(null);
						AzureAPI.log(player, ConfigPatch.AntiLongStringCrashWarnMessage);
					}
				}
			}
		}
	}
}
