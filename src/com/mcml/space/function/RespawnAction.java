package com.mcml.space.function;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import lombok.val;

import static com.mcml.space.config.ConfigFunction.canAutoRespawn;
import static com.mcml.space.config.ConfigFunction.sendTitleAutoRespawn;
import static com.mcml.space.config.ConfigFunction.titleAutoRespawn;

import static com.mcml.space.config.ConfigFunction.subtitleAutoRespawn;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class RespawnAction implements Listener, PluginExtends {
    public static void init(JavaPlugin plugin) {
        if (!VersionLevel.isSpigot()) {
            AzureAPI.warn("非 Spigot 服务端不支持自动重生功能");
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
                	if(VersionLevel.isSpigot()){
                		player.spigot().respawn();
                        if (sendTitleAutoRespawn) {
                        	if(VersionLevel.isHigherEquals(Version.MINECRAFT_1_8_R2)){
                        		player.sendTitle(titleAutoRespawn, subtitleAutoRespawn);
                        	}else{
                        		AzureAPI.log("错误！你不应该开启自动复活的title显示，无法显示！因为你的服务器版本低于1.8.4!");
                        	}
                        }
                	}
                }
            }, 1);
        }
    }
}
