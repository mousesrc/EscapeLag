package com.mcml.space.function;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Perms;

import lombok.val;

import static com.mcml.space.config.ConfigFunction.preventSpawnerModify;
import static com.mcml.space.config.ConfigFunction.messagePreventSpawnerModify;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class SpawnerController implements Listener {
    public static SpawnerController init(JavaPlugin plugin) {
        SpawnerController instance = new SpawnerController();
        Bukkit.getPluginManager().registerEvents(instance, plugin);
        AzureAPI.log("刷怪笼控制模块已启动");
        return instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onModify(PlayerInteractEvent evt) {
        if (!preventSpawnerModify || evt.getItem() == null || evt.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        val player = evt.getPlayer();
        if (Perms.has(player)) return;
        
        if (evt.getClickedBlock().getType() == Material.MOB_SPAWNER) {
            val type = evt.getItem().getType();
            if (type == Material.MONSTER_EGG || type == Material.MONSTER_EGGS) {
                evt.setCancelled(true);
                AzureAPI.log(player, messagePreventSpawnerModify);
            }
        }
    }
}
