package com.amd.sensemi.patch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.config.Patch;

public class AntiCrashSign implements Listener {

    @EventHandler
    public void SignCheckChange(SignChangeEvent event) {
        if (Patch.fixCrashSign) {
            Player player = event.getPlayer();
            String[] lines =event.getLines();
            int ll = lines.length;
            for(int i = 0;i<ll;i++){
                String line = lines[i];
                if(line.length() >= 127){
                    event.setCancelled(true);
                    AzureAPI.log(player, Patch.AntiCrashSignWarnMessage);
                }
            }
        }
    }
}