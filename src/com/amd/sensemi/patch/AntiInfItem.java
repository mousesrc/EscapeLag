package com.amd.sensemi.patch;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.config.Patch;

public class AntiInfItem implements Listener {

    @EventHandler
    @SuppressWarnings("deprecation")
    public void InteractCheck(PlayerInteractEvent event) {
        if (Patch.noInfItem) {
            Player player = event.getPlayer();
            if (event.getItem() != null) {
                if (event.getItem().getAmount() <= 0) {
                    event.setCancelled(true);
                    player.setItemInHand(null);
                    AzureAPI.log(player, Patch.AntiInfItemClickcWarnMessage);
                }
            }
        }
    }

    @EventHandler
    public void DispenseCheck(BlockDispenseEvent event) {
        if (Patch.noInfItem == true) {
            Block block = event.getBlock();
            int i;
            if (block.getType() == Material.DISPENSER) {
                BlockState blockin = block.getState();
                Inventory Inventory = ((Dispenser) blockin).getInventory();
                int invs = Inventory.getSize();
                for (i = 0; i < invs; i++) {
                    if (Inventory.getItem(i) != null) {
                        if (Inventory.getItem(i).getAmount() <= 0) {
                            Inventory.getItem(i).setType(Material.AIR);
                            event.setCancelled(true);
                        }
                    }
                }
            } else if (block.getType() == Material.DROPPER) {
                BlockState blockin = block.getState();
                Inventory Inventory = ((Dropper) blockin).getInventory();
                int invs = Inventory.getSize();
                for (i = 0; i < invs; i++) {
                    if(Inventory.getItem(i) != null){
                        if (Inventory.getItem(i).getAmount() <= 0) {
                            Inventory.getItem(i).setType(Material.AIR);
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}