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
	private int nowint;

	@EventHandler
	public void TeleportLoader(final PlayerTeleportEvent event) {
		if (ConfigOptimize.TeleportPreLoaderenable == true) {
			if (isPreLoading == false) {
				isPreLoading = true;
				event.setCancelled(true);
				final Player player = event.getPlayer();
				final List<Chunk> chunks = Utils.getShouldUseChunk(event.getTo());
				final int cs = chunks.size();
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 1);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 2);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 3);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 4);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 5);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 6);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 7);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 8);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						nowint = nowint + cs/10;
						for (int i = nowint - cs/10; i < nowint; i++) {
							Chunk chunk = chunks.get(i);
							if (chunk.isLoaded() == false) {
								chunk.load();
							}
						}
					}
				}, 9);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					public void run() {
						player.teleport(event.getTo());
						isPreLoading = false;
						nowint = 0;
					}
				}, 10);
			}
		}
	}
}
