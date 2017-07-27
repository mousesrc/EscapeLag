package com.amd.sensemi.function;

import static com.amd.sensemi.config.Function.canAutoRespawn;
import static com.amd.sensemi.config.Function.sendTitleAutoRespawn;
import static com.amd.sensemi.config.Function.subtitleAutoRespawn;
import static com.amd.sensemi.config.Function.titleAutoRespawn;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.amd.sensemi.VLagger;

import lombok.val;

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
