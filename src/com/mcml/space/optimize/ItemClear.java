package com.mcml.space.optimize;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.Utils;

public class ItemClear implements Listener {

    public static ArrayList<Chunk> DeathChunk = new ArrayList<Chunk>();
    // TODO configurable type, clear mobs - tons
    @EventHandler
    public void ChunkUnloadClear(ChunkUnloadEvent event) {
    	if(ConfigOptimize.ClearItemenable != true){
            return;
        }
        Chunk chunk = event.getChunk();
        if (DeathChunk.contains(chunk) == false) { // TODO slow
            Entity[] entities = chunk.getEntities();
            for (int i = 0; i < entities.length; i++) {
                Entity ent = entities[i];
                if (ent.getType() == EntityType.DROPPED_ITEM) {
                    if(ConfigOptimize.ClearItemNoClearItemType.contains(ent.getType().name()) == false){
                        ent.remove();
                    }
                }
            }
        }else{
        	DeathChunk.remove(chunk);
        }
    }

    @EventHandler
    public void DeathNoClear(PlayerDeathEvent event) {
        if(ConfigOptimize.ClearItemNoCleatDeath != true){
            return;
        }
        Player player = event.getEntity();
        Chunk chunk = player.getLocation().getChunk();
        List<Chunk> chunks = Utils.getnearby9chunks(chunk);
        DeathChunk.addAll(chunks);
    }

    @EventHandler
    public void TeleportNoClear(PlayerTeleportEvent event){
        if(ConfigOptimize.ClearItemNoClearTeleport != true){
            return;
        }
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();
        List<Chunk> chunks = Utils.getnearby9chunks(chunk);
        DeathChunk.addAll(chunks);
    }
}
