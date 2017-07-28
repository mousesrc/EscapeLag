package com.mcml.space.function;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.Maps;
import com.mcml.space.config.ConfigFunction;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzurePlayerList;
import com.mcml.space.util.QuitReactor;

import lombok.val;

public class AntiSpam implements Listener, QuitReactor {
    private final Map<Player, Long> timeRecord;

    public AntiSpam() {
        timeRecord = Maps.newHashMap();
        AzurePlayerList.bind(this);
    }
    
    private boolean isSpamming(Player player, long now) {
        Long lastChat = timeRecord.get(player);
        if (lastChat == null) return false;

        return System.currentTimeMillis() - lastChat.longValue() <= ConfigFunction.AntiSpamPeriodPeriod * 1000;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void spamChecker(AsyncPlayerChatEvent evt) {
        if (!ConfigFunction.AntiSpamenable) return;
        
        val player = evt.getPlayer();
        if (AzureAPI.hasPerm(player, "VLagger.bypass.Spam")) {
            return;
        }
        
        val now = System.currentTimeMillis();
        if (isSpamming(player, now)) {
            evt.setCancelled(true);
            AzureAPI.log(player, ConfigFunction.AntiSpamPeriodWarnMessage);
        } else {
            timeRecord.put(player, now);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void checkDirty(AsyncPlayerChatEvent evt) {
        if (ConfigFunction.AntiSpamenable && ConfigFunction.enableAntiDirty) {
            val player = evt.getPlayer();
            String message = evt.getMessage().toLowerCase();
            if (AzureAPI.hasPerm(player, "VLagger.bypass.Spam")) {
                return;
            }
            
            for (String each : ConfigFunction.AntiSpamDirtyList) {
                boolean deny = true;
                for (char c : each.toLowerCase().toCharArray()) {
                    if (!StringUtils.contains(message, c)) deny = false;
                }
                if (deny) {
                    evt.setCancelled(true);
                    AzureAPI.log(player, ConfigFunction.AntiSpamDirtyWarnMessage);
                }
            }
        }
    }

    @Override
    public void react(PlayerQuitEvent evt) {
        timeRecord.remove(evt.getPlayer());
    }
}
