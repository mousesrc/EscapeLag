package com.mcml.space.fix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.mcml.space.config.ConfigFixing;
import com.mcml.space.util.AzureAPI;

public class AntiCrashChat implements Listener{
    private boolean HasEss;

    public AntiCrashChat(){
        Plugin ess = Bukkit.getPluginManager().getPlugin("Essentials");
        if(ess != null){
            HasEss = true;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void ChatCheckCrash(AsyncPlayerChatEvent event){
        if (!ConfigFixing.noCrashChat) return;
        
        Player player = event.getPlayer();
        String message = event.getMessage();
        if(message.contains("İ")){
            event.setCancelled(true);
            if(ConfigFixing.AntiCrashChatSpecialStringWarnMessage.equalsIgnoreCase("none") == false){
                AzureAPI.log(player, ConfigFixing.AntiCrashChatSpecialStringWarnMessage);
            }
        }
        if(HasEss == true){
            if(message.contains("&")){
                event.setCancelled(true);
                if(ConfigFixing.AntiCrashChatSpecialStringWarnMessage.equalsIgnoreCase("none") == false){
                    AzureAPI.log(player, ConfigFixing.AntiCrashChatSpecialStringWarnMessage);
                }
            }
        }
    }
}
// TODO confirm details