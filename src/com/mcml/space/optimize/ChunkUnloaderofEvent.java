package com.mcml.space.optimize;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.AzureAPI;

public class ChunkUnloaderofEvent implements Listener {

	public static void init(Plugin JavaPlugin) {
		Bukkit.getPluginManager().registerEvents(new ChunkUnloaderofEvent(), JavaPlugin);
		AzureAPI.log("区块卸载系统现在运行...");
	}

	@EventHandler
	public void LeaveWorldCheck(PlayerChangedWorldEvent event) {
		if (ConfigOptimize.chunkUnloader == true && event.getFrom().getPlayers().isEmpty()) {
			Chunk[] loadedChunks = event.getFrom().getLoadedChunks();
			int lcl = loadedChunks.length;
			for (int i = 0; i < lcl; i++) {
				Chunk chunk = loadedChunks[i];
				if (chunk.isLoaded() == true & ChunkKeeper.ShouldKeepList.contains(chunk) == false) {
					chunk.unload();
					ChunkUnloaderofRunnable.ChunkUnloaderTimes++;
				}
			}
		}
	}
}
