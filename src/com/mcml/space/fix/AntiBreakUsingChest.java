package com.mcml.space.fix;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;

import com.mcml.space.config.ConfigFixing;
import com.mcml.space.util.AzureAPI;

/**
 * @author jiongjionger
 */
public class AntiBreakUsingChest implements Listener {

    @EventHandler
    public void CheckNoBreakChest(BlockBreakEvent e) {
        if (ConfigFixing.protectUsingChest) {
            Player p = e.getPlayer();
            if (e.getBlock().getState() instanceof InventoryHolder) {
                InventoryHolder ih = (InventoryHolder) e.getBlock().getState();
                if (ih.getInventory().getViewers().isEmpty() == false) {
                    e.setCancelled(true);
                    if(ConfigFixing.AntiBreakUsingChestWarnMessage.equalsIgnoreCase("none") == false){
                        AzureAPI.log(p, ConfigFixing.AntiBreakUsingChestWarnMessage);
                    }
                }
            }
        }
    }
}
