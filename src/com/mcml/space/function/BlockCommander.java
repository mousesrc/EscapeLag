package com.mcml.space.function;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.mcml.space.config.ConfigFunction;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Perms;

import lombok.val;

public class BlockCommander implements Listener {
    @EventHandler
    public void process(PlayerCommandPreprocessEvent evt) {
        if (ConfigFunction.canBlockCommand) {
            val player = evt.getPlayer();
            FileConfiguration config = VLagger.configFunction.getValue();
            if (Perms.has(player)) return;
            
            if (config.getBoolean("BlockCommander.List." + player.getWorld().getName() + "." + evt.getMessage())) {
                evt.setCancelled(true);
                AzureAPI.log(player, config.getString("BlockCommander.List." + player.getWorld().getName() + "." + evt.getMessage() + ".Message"));
            }
        }
    }
}
