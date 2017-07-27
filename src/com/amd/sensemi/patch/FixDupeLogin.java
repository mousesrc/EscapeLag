package com.amd.sensemi.patch;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.amd.sensemi.api.AzurePlayerList;
import com.amd.sensemi.config.Patch;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class FixDupeLogin implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(AsyncPlayerPreLoginEvent evt) {
        if (Patch.fixDupeOnline) {
            if (AzurePlayerList.contains(evt.getName())) {
                evt.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                evt.setKickMessage(Patch.messageKickDupeOnline);
            }
        }
    }
    
}
