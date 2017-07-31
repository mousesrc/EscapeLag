package com.mcml.space.patch;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;

public class BonemealDupePatch implements Listener {

    @EventHandler
    public void TreeGrowChecker(StructureGrowEvent event) {
        if (ConfigPatch.safetyBonemeal) {
        	List<BlockState> blocks = event.getBlocks();
        	int bs = blocks.size();
        	for(int i = 0;i<bs;i++){
        		Block block = blocks.get(i).getBlock();
        		if(block.getType() != Material.AIR && block.getType() != Material.SAPLING && block.getType() != event.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()){
        			event.setCancelled(true);
                    if (event.getPlayer() != null) {
                        AzureAPI.log(event.getPlayer(), "§c这棵树生长区域有方块阻挡，请不要尝试利用骨粉BUG！");
                        return;
                    }
        		}
        	}
        }
    }
}