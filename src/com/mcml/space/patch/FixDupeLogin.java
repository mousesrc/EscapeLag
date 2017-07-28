package com.mcml.space.patch;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import com.mcml.space.config.ConfigPatch;
import com.mcml.space.util.AzurePlayerList;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class FixDupeLogin implements Listener {

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
