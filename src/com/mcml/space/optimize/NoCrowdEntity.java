package com.mcml.space.optimize;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.AzureAPI;

import lombok.val;

public class NoCrowdEntity implements Listener {
    
    @EventHandler
    @SuppressWarnings("all")
    public void CheckCrowd(ChunkLoadEvent evt) {
        if (ConfigOptimize.NoCrowdedEntityenable) {
            val chunk = evt.getChunk();
            val entities = chunk.getEntities();
            
            for (Entity e : entities) {
                val type = e.getType();
                int count = 0;
                if (AzureAPI.containsIgnoreCase(ConfigOptimize.NoCrowdedEntityTypeList, type.name())) {
                    count++;
                    if (count > ConfigOptimize.NoCrowdedEntityPerChunkLimit && e.getType() != EntityType.PLAYER) {
                        e.remove();
                    }
                }
            }
            
        }
    }
}
