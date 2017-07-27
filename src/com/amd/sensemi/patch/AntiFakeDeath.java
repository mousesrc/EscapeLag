package com.amd.sensemi.patch;

import java.util.List;

import org.bukkit.entity.Player;

import com.amd.sensemi.api.AzurePlayerList;
import com.amd.sensemi.config.Patch;

public class AntiFakeDeath implements Runnable{

    @Override
    public void run() {
        if(Patch.noFakedeath){
            List<Player> players = AzurePlayerList.players();
            int ps = players.size();
            for(int i = 0;i<ps;i++){
                Player player = players.get(i);
                if(player.getHealth() <= 0 && !player.isDead()){
                    player.setHealth(0.0);
                    player.kickPlayer(Patch.messageFakedeath);
                }
            }
        }
    }
}
