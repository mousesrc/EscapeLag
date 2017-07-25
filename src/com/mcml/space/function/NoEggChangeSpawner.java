package com.mcml.space.function;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import com.mcml.space.config.ConfigFunction;
import com.mcml.space.config.ConfigMain;
import com.mcml.space.util.AzureAPI;

import org.bukkit.Material;

public class NoEggChangeSpawner implements Listener {

    @EventHandler
    public void NoChangeLimit(PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        }
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getPlayer().hasPermission("VLagger.admin")) {
            return;
        }
        if (ConfigFunction.NoEggChangeSpawnerenable == true) {
            if (e.getItem().getType() == Material.MONSTER_EGG || e.getItem().getType() == Material.MONSTER_EGGS) {
                if (e.getClickedBlock().getType() == Material.MOB_SPAWNER) {
                    e.setCancelled(true);
                    if(ConfigFunction.NoEggChangeSpawnerTipMessage.equalsIgnoreCase("none") == false){
                        AzureAPI.log(e.getPlayer(), ConfigFunction.NoEggChangeSpawnerTipMessage);
                    }
                }
            }
        }
    }
}
// TODO check op(permission utils), right-click only