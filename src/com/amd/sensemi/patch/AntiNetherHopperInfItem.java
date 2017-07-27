package com.amd.sensemi.patch;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;

import com.amd.sensemi.config.Patch;

public class AntiNetherHopperInfItem implements Listener {

    @EventHandler
    public void onHopper(InventoryMoveItemEvent event) {
        if (Patch.fixNetherHopperInfItem) {
            if (event.getInitiator().getHolder() instanceof Hopper && event.getSource().getHolder() instanceof Hopper) {
                Hopper to = (Hopper) event.getInitiator().getHolder();
                Hopper from = (Hopper)event.getSource().getHolder();
                if (to.getWorld().getEnvironment() == World.Environment.NETHER) {
                    if(to.getChunk() != from.getChunk()){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
