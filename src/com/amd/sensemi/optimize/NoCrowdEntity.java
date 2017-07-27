package com.amd.sensemi.optimize;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import com.amd.sensemi.config.Optimize;

import lombok.val;

public class NoCrowdEntity implements Listener {
    
    @EventHandler
    @SuppressWarnings("all")
    public void CheckCrowd(ChunkLoadEvent evt) {
        if (Optimize.NoCrowdedEntityenable) {
            val chunk = evt.getChunk();
            val entities = chunk.getEntities();
            
            for (Entity e : entities) {
                val type = e.getType();
                int count = 0;
                if (Optimize.NoCrowdedEntityTypeList.contains(type.getName())) {
                    count++;
                    if (count > Optimize.NoCrowdedEntityPerChunkLimit && e.getType() != EntityType.PLAYER) {
                        e.remove();
                    }
                }
            }
            
        }
    }
}
