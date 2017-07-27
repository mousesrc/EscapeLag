package com.mcml.space.fix;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;

import com.mcml.space.config.Fixes;
import com.mcml.space.util.AzureAPI;

/**
 * @author jiongjionger
 */
public class AntiBreakUsingChest implements Listener {

    @EventHandler
    public void CheckNoBreakChest(BlockBreakEvent e) {
        if (Fixes.protectUsingChest) {
            Player p = e.getPlayer();
            if (e.getBlock().getState() instanceof InventoryHolder) {
                InventoryHolder ih = (InventoryHolder) e.getBlock().getState();
                if (ih.getInventory().getViewers().isEmpty() == false) {
                    e.setCancelled(true);
                    AzureAPI.log(p, Fixes.AntiBreakUsingChestWarnMessage);
                }
            }
        }
    }
}
