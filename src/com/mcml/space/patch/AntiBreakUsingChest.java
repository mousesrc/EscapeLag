package com.mcml.space.patch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;

/**
 * @author jiongjionger
 */
public class AntiBreakUsingChest implements Listener {

    @EventHandler
    public void CheckNoBreakChest(BlockBreakEvent e) {
        if (ConfigPatch.protectUsingChest) {
            Player p = e.getPlayer();
            if (e.getBlock().getState() instanceof InventoryHolder) {
                InventoryHolder ih = (InventoryHolder) e.getBlock().getState();
                if (ih.getInventory().getViewers().isEmpty() == false) {
                    e.setCancelled(true);
                    AzureAPI.log(p, ConfigPatch.AntiBreakUsingChestWarnMessage);
                }
            }
        }
    }
}
