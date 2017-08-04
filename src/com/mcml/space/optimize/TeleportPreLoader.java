package com.mcml.space.optimize;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI.Coord;
import com.mcml.space.util.Utils;

public class TeleportPreLoader implements Listener {

	private boolean isPreLoading;
	private int nowteleportid = 0;
	private HashMap<Integer, Integer> nowint = new HashMap<Integer, Integer>();

	@EventHandler
	public void TeleportLoader(final PlayerTeleportEvent event) {
		if (ConfigOptimize.TeleportPreLoaderenable == true) {
			final Player player = event.getPlayer();
			if (event.getFrom().equals(event.getTo())){
				event.setCancelled(true);
			}
			if (player.getVehicle() != null) {
				return;
			}
			if (event.getCause() == TeleportCause.END_GATEWAY) {
				return;
			}
			nowteleportid++;
			if (isPreLoading == false) {
				event.setCancelled(true);
				final int thistpid = nowteleportid;
				final List<Coord<Integer, Integer>> chunks = Utils.getShouldUseChunks(event.getTo());
				final int cs = chunks.size();
				if (nowint.get(thistpid) == null) {
					nowint.put(thistpid, 0);
				}
				final World world = event.getTo().getWorld();
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
							Coord<Integer, Integer> coord = chunks.get(i);
							world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 1);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 2);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 3);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 4);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 5);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 6);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 7);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 8);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						nowint.put(thistpid, nowint.get(thistpid) + cs / 10);
						for (int i = nowint.get(thistpid) - cs / 10; i < nowint.get(thistpid); i++) {
						    Coord<Integer, Integer> coord = chunks.get(i);
                            world.loadChunk(coord.getKey(), coord.getValue());
						}
					}
				}, 9);
				Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
					@Override
                    public void run() {
						isPreLoading = true;
						player.teleport(event.getTo());
						isPreLoading = false;
						nowint.remove(thistpid);
					}
				}, 10);
			}
		}
	}
}
