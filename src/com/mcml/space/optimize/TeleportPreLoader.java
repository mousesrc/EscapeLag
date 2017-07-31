package com.mcml.space.optimize;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.Utils;

public class TeleportPreLoader implements Listener {

	private boolean isPreLoading;

	@EventHandler
	public void TeleportLoader(final PlayerTeleportEvent event) {
		Bukkit.broadcastMessage("传送监听器被触发！" + isPreLoading);
		if (ConfigOptimize.TeleportPreLoaderenable == true) {
			if (isPreLoading == false) {
				isPreLoading = true;
				event.setCancelled(true);
				final Player player = event.getPlayer();
				final List<Chunk> chunks = Utils.getShouldUseChunk(event.getTo());
				final int cs = chunks.size();
				final int first = 0 + cs / 5;
				final int second = first + cs / 5;
				final int third = second + cs / 5;
				final int fourth = third + cs / 5;
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						for (int i = 0; i < first; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 2);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						for (int i = first; i < second; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 4);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						for (int i = second; i < third; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 6);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						for (int i = third; i < fourth; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 8);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						player.teleport(event.getTo());
						isPreLoading = false;
					}
				}, 10);
			}
		}
	}
}
