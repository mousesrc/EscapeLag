package com.mcml.space.function;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.mcml.space.core.VLagger;

import lombok.val;

import static com.mcml.space.config.Functions.canAutoRespawn;
import static com.mcml.space.config.Functions.sendTitleAutoRespawn;
import static com.mcml.space.config.Functions.titleAutoRespawn;
import static com.mcml.space.config.Functions.subtitleAutoRespawn;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class RespawnAction implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void autoRespawn(PlayerDeathEvent evt) {
        if (canAutoRespawn) {
            val player = evt.getEntity();
            Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
                @Override
                @SuppressWarnings("all")
                public void run() {
                    player.spigot().respawn();
                    
                    if (sendTitleAutoRespawn) {
                        player.sendTitle(titleAutoRespawn, subtitleAutoRespawn);
                    }
                }
            }, 1);
        }
    }
}
