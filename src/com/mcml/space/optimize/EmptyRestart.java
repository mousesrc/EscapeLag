package com.mcml.space.optimize;

import static com.mcml.space.config.Functions.emptyRestart;
import static com.mcml.space.config.Functions.emptyRestartDelay;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzurePlayerList;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class EmptyRestart implements Listener {
    private static int restartTaskId = -1;

    @EventHandler(priority = EventPriority.MONITOR)
    public void preparRestart(PlayerQuitEvent evt){
        if(AzurePlayerList.isEmpty() && emptyRestart && restartTaskId == -1){
            restartTaskId = Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable(){
                @Override
                public void run(){
                    AzureAPI.tryRestartServer();
                }
            }, AzureAPI.toTicks(TimeUnit.SECONDS, emptyRestartDelay)).getTaskId();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void cancelRestart(PlayerJoinEvent evt){
        if(emptyRestart) {
            Bukkit.getScheduler().cancelTask(restartTaskId);
            restartTaskId = -1;
        }
    }
    
}