package com.amd.sensemi.function;

import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import com.amd.sensemi.VLagger;
import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.config.Function;

public class BlockCommander implements Listener {

    @EventHandler
    public void CommanderBlocker(PlayerCommandPreprocessEvent event) {
        if (Function.BlockCommanderenable == true) {
            Player p = event.getPlayer();
            FileConfiguration config = AzureAPI.loadOrCreateFile(VLagger.functionConfiguation);
            if (p.hasPermission("VLagger.admin") == true) {
                return;
            }
            if (config.getBoolean("BlockCommander.List." + p.getWorld().getName() + "." + event.getMessage())) {
                event.setCancelled(true);
                AzureAPI.log(p, config.getString("BlockCommander.List." + p.getWorld().getName() + "." + event.getMessage() + ".Message"));
            }
        }
    }
}
