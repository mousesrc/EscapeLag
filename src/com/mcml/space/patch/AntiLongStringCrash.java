package com.mcml.space.patch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;

public class AntiLongStringCrash implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void ClickCheckItem(PlayerInteractEvent event){
		if(ConfigPatch.AntiLongStringCrashenable == true){
			ItemStack item = event.getItem();
			Player player = event.getPlayer();
			if(item != null){
				if(item.hasItemMeta()){
					if(item.getItemMeta().getDisplayName().length() > 20){
						player.setItemInHand(null);
						AzureAPI.log(player, ConfigPatch.AntiLongStringCrashWarnMessage);
					}
				}
			}
		}
	}
}
