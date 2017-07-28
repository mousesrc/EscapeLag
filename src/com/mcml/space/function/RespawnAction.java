package com.mcml.space.function;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.VersionLevel;

import lombok.val;

import static com.mcml.space.config.ConfigFunction.canAutoRespawn;
import static com.mcml.space.config.ConfigFunction.sendTitleAutoRespawn;
import static com.mcml.space.config.ConfigFunction.titleAutoRespawn;
import static com.mcml.space.config.ConfigFunction.subtitleAutoRespawn;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class RespawnAction implements Listener {
    public static void init(JavaPlugin plugin) {
        if (!VersionLevel.isSpigot()) {
            AzureAPI.log("当前的服务端不支持自动重生功能");
            return;
        }
        Bukkit.getPluginManager().registerEvents(new RespawnAction(), plugin);
        AzureAPI.log("自动重生模块已启动");
    }
    
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
