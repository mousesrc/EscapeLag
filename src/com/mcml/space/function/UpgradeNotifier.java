package com.mcml.space.function;

import org.bukkit.event.player.PlayerJoinEvent;

import com.mcml.space.config.ConfigMain;
import com.mcml.space.util.JoinReactor;
import com.mcml.space.util.Perms;

import lombok.val;

public class UpgradeNotifier implements JoinReactor {
    @Override
    public void react(PlayerJoinEvent evt) {
        if(ConfigMain.AutoUpdate) return;
        
        val player = evt.getPlayer();
        if(Perms.has(player)){
            player.sendMessage("§a§l[VLagger]§e提示:§b输入/vlg updateon 来开启自动更新，永远保持你的服务器运行高效！"); // TODO better way?
        }
    }
    
}
