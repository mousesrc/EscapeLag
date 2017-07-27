package com.amd.sensemi.optimize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.amd.sensemi.VLagger;
import com.amd.sensemi.api.AzurePlayerList;
import com.amd.sensemi.config.Optimize;

public class ChunkKeeper implements Listener {
    // TODO clear
    public static HashMap<Chunk, Integer> ChunkTimes = new HashMap<Chunk, Integer>();
    public static ArrayList<Chunk> ShouldKeepList = new ArrayList<Chunk>();

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (ShouldKeepList.contains(event.getChunk()) & Optimize.ChunkKeeperenable == true) {
            event.setCancelled(true);
        }
    }

    public static void ChunkKeeperofTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VLagger.MainThis, new Runnable() {

            @Override
            public void run() {
                ChunkKeeper.ChunkTimes.clear();
            }
        }, 60 * 60 * 20, 60 * 60 * 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VLagger.MainThis, new Runnable() {

            @Override
            public void run() {
                if (Optimize.ChunkKeeperenable == true) {
                    List<Player> onlinePlayers = AzurePlayerList.players();
                    if (!onlinePlayers.isEmpty()) {
                        Iterator<? extends Player> players = onlinePlayers.iterator();
                        while (players.hasNext()) {
                            Player player = players.next();
                            Chunk chunk = player.getLocation().getChunk();
                            if (ChunkTimes.get(chunk) == null) {
                                ChunkTimes.put(chunk, 1);
                            } else {
                                ChunkTimes.put(chunk, ChunkTimes.get(chunk) + 1);
                            }
                            if (ChunkTimes.get(chunk) > AzurePlayerList.size() & ShouldKeepList.contains(chunk)==false) {
                                if(ShouldKeepList.size() > AzurePlayerList.size()){
                                    ShouldKeepList.remove(0);
                                }
                                ShouldKeepList.add(chunk);
                            }
                        }
                    }
                }
            }
        }, 15 * 20, 15 * 20);
    }
}
