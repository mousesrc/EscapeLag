package com.mcml.space.patch;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzurePlayerList;
import com.mcml.space.util.PluginExtends;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class DupeLoginPatch implements Listener, PluginExtends {
    public static void init(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new DupeLoginPatch(), plugin); // TODO 开关从配置文件移过来
        AzureAPI.log("多重在线修复模块已启用");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(AsyncPlayerPreLoginEvent evt) {
        if (ConfigPatch.fixDupeOnline) {
            if (AzurePlayerList.contains(evt.getName())) {
                evt.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                evt.setKickMessage(ConfigPatch.messageKickDupeOnline);
            }
        }
    }
    
}
