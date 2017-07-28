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
            if (AzureAPI.hasPerm(p, "VLagger.bypass.Spam")) {
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
            String message = event.getMessage().toLowerCase();
            if (AzureAPI.hasPerm(p, "VLagger.bypass.Spam")) {
                return;
            }
            
            for (String each : ConfigFunction.AntiSpamDirtyList) {
                boolean deny = true;
                for (char c : each.toLowerCase().toCharArray()) {
                    if (!StringUtils.contains(message, c)) deny = false;
                }
                if (deny) {
                    event.setCancelled(true);
                    AzureAPI.log(p, ConfigFunction.AntiSpamDirtyWarnMessage);
                }
            }
        }
    }
}
