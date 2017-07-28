package com.mcml.space.patch;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;

import static com.mcml.space.config.ConfigPatch.fixRPGItemInfItem;

/**
 * @author Vlvxingze
 */
public class RPGItemPatch implements Listener, PluginExtends {
    public static void init(JavaPlugin plugin) {
        if (!ConfigPatch.forceRPGItemPatch && !Bukkit.getPluginManager().isPluginEnabled("RPGItems") && !Bukkit.getPluginManager().isPluginEnabled("RPG Items")) return;
        
        Bukkit.getPluginManager().registerEvents(new RPGItemPatch(), plugin);
        AzureAPI.log("RPGItem 修复模块已启用");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickup(PlayerPickupItemEvent evt) {
        if (!fixRPGItemInfItem) return;
        
        if (evt.getRemaining() <= 0) {
            evt.getItem().remove();
        }
    }
    
}