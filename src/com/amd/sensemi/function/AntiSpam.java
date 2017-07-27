package com.amd.sensemi.function;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.config.Function;

public class AntiSpam implements Listener {

    private final static HashMap<String, Long> CheckList = new HashMap<String, Long>(); // TODO thread-safe, notice memory usage

    private static boolean CheckFast(String bs) {
        if (CheckList.containsKey(bs)) {
            return (CheckList.get(bs).longValue() + Function.AntiSpamPeriodPeriod * 1000 > System.currentTimeMillis());
        }
        return false;
    }

    @EventHandler
    public void SpamChecker(AsyncPlayerChatEvent event) {
        if (Function.AntiSpamenable) {
            Player p = event.getPlayer();
            if (p.hasPermission("VLagger.bypass.Spam")) { // TODO add permission utils
                return;
            }
            String pn = p.getName();
            if (CheckFast(pn)) {
                event.setCancelled(true);
                AzureAPI.log(p, Function.AntiSpamPeriodWarnMessage);
            }else{
                CheckList.put(pn, System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void AntiDirty(AsyncPlayerChatEvent event) {
        if (Function.AntiSpamenable) {
            Player p = event.getPlayer();
            String message = event.getMessage();
            if (p.hasPermission("VLagger.bypass.Spam")) {
                return;
            }
            List<String[]> DirtyListStrings = Function.AntiSpamDirtyListStrings();
            int dlsl = DirtyListStrings.size();
            for(int i = 0;i<dlsl;i++){
            	String[] thisdirty = DirtyListStrings.get(i);
            	int DirtyTimes = 0;
            	int tdl = thisdirty.length;
            	for(int ii = 0;ii<tdl;ii++){
            		if(message.contains(thisdirty[ii])){
            			DirtyTimes++;
            		}
            	}
            	if(DirtyTimes >= tdl){
            		event.setCancelled(true);
                    AzureAPI.log(p, Function.AntiSpamDirtyWarnMessage);
            	}
            }
        }
    }
}
