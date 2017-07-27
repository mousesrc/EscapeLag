package com.amd.sensemi.patch;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.amd.sensemi.config.Patch;

/**
 * @author jiongjionger
 */
public class AntiDoorInfItem implements Listener {

    @EventHandler
    public void PlaceCheckDoor(BlockPlaceEvent e) {
        if(Patch.fixDoorInfItem){
            if(e.getBlock().getType().name().contains("DOOR")){
                Player p = e.getPlayer();
                Chunk chunk = p.getLocation().getChunk();
                Entity[] entities = chunk.getEntities();
                for(int i=0;i<entities.length;i++){
                    Entity ent = entities[i];
                    if(ent.getType() == EntityType.DROPPED_ITEM){
                        Item item = (Item)ent;
                        if(item.getItemStack().getType() == Material.SUGAR_CANE||item.getItemStack().getType() == Material.CACTUS){
                            ent.remove();
                        }
                    }
                }
            }
        }
    }
}
