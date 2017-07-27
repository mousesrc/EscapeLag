package com.mcml.space.fix;

import java.util.List;

import org.bukkit.entity.Player;

import com.mcml.space.config.Fixes;
import com.mcml.space.util.AzurePlayerList;

public class AntiFakeDeath implements Runnable{

    @Override
    public void run() {
        if(Fixes.noFakedeath){
            List<Player> players = AzurePlayerList.players();
            int ps = players.size();
            for(int i = 0;i<ps;i++){
                Player player = players.get(i);
                if(player.getHealth() <= 0 && !player.isDead()){
                    player.setHealth(0.0);
                    player.kickPlayer(Fixes.messageFakedeath);
                }
            }
        }
    }
}
