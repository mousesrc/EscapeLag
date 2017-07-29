package com.mcml.space.patch;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import com.mcml.space.config.ConfigPatch;

public class BonemealDupePatch implements Listener {

    @EventHandler
    public void TreeGrowChecker(StructureGrowEvent event) {
        if (ConfigPatch.safetyBonemeal) {
            Location loc = event.getLocation();
            Block block = loc.getBlock();
            if (block.getRelative(BlockFace.UP).getType() != Material.AIR) {
                event.setCancelled(true);
                if (event.getPlayer() != null) {
                    event.getPlayer().sendMessage(ConfigPatch.messageBonemeal);
                }
            }
        }
    }

    @EventHandler
    public void BoneGrowBlocker(StructureGrowEvent event) {
        if(ConfigPatch.safetyBonemeal){
            if (event.isFromBonemeal()) {
                event.setCancelled(true);
                if (event.getPlayer() != null) {
                    event.getPlayer().sendMessage(ConfigPatch.messageBonemeal);
                }
            }
        }
    }
}
// TODO mod support, using type id