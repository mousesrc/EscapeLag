package com.amd.sensemi.optimize;

import static com.amd.sensemi.config.Optimize.noSpawnChunks;
import static com.amd.sensemi.config.Optimize.nscExcludeWorlds;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import com.amd.sensemi.api.AzureAPI;

import lombok.val;

public class NoSpawnChunks implements Listener {
    
    @EventHandler
    public void onWorldLoad(WorldInitEvent evt) {
        if (noSpawnChunks) {
            val world = evt.getWorld();
            if (nscExcludeWorlds.isEmpty() || !nscExcludeWorlds.contains(world.getName())) {
                world.setKeepSpawnInMemory(false);
                AzureAPI.log("已为世界 " + world.getName() + " 设定不保留出生区块.");
            }
        }
    }
}
