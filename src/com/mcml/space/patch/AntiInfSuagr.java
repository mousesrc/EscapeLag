package com.mcml.space.patch;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.mcml.space.config.ConfigPatch;

public class AntiInfSuagr implements Listener {

    @EventHandler
    public void PlaceCheckDoor(BlockPlaceEvent e) {
        if(ConfigPatch.AntiInfSuagrenable){
			if(e.isCancelled()){
				Player p = e.getPlayer();
                List<Entity> entities = p.getNearbyEntities(2, 2, 2);
                for(int i=0;i<entities.size();i++){
                    Entity ent = entities.get(i);
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
