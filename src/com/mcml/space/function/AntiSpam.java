package com.mcml.space.function;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mcml.space.config.ConfigFunction;
import com.mcml.space.util.AzureAPI;

public class AntiSpam implements Listener {

    private final static HashMap<String, Long> CheckList = new HashMap<String, Long>(); // TODO thread-safe, notice memory usage

    private static boolean CheckFast(String bs) {
        if (CheckList.containsKey(bs)) {
            return (CheckList.get(bs).longValue() + ConfigFunction.AntiSpamPeriodPeriod * 1000 > System.currentTimeMillis());
        }
        return false;
    }

    @EventHandler
    public void SpamChecker(AsyncPlayerChatEvent event) {
        if (ConfigFunction.AntiSpamenable) {
            Player p = event.getPlayer();
            if (p.hasPermission("VLagger.bypass.Spam")) { // TODO add permission utils
                return;
            }
            String pn = p.getName();
            if (CheckFast(pn)) {
                event.setCancelled(true);
                AzureAPI.log(p, ConfigFunction.AntiSpamPeriodWarnMessage);
            }else{
                CheckList.put(pn, System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void AntiDirty(AsyncPlayerChatEvent event) {
        if (ConfigFunction.AntiSpamenable) {
            Player p = event.getPlayer();
            String message = event.getMessage();
            if (p.hasPermission("VLagger.bypass.Spam")) {
                return;
            }
            
            for (String each : ConfigFunction.AntiSpamDirtyList) {
                if (StringUtils.containsAny(message, each)) {
                    event.setCancelled(true);
                    AzureAPI.log(p, ConfigFunction.AntiSpamDirtyWarnMessage);
                }
            }
            
        }
    }
}
