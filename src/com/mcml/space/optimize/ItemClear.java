package com.mcml.space.optimize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.google.common.collect.Lists;
import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.AzureAPI.Coord3;
import com.mcml.space.util.Utils;

public class ItemClear implements Listener {
	public static ArrayList<Coord3<Integer, Integer, World>> DeathChunk = Lists.newArrayList();

	@SuppressWarnings("deprecation")
    @EventHandler
	public void ChunkloadClear(ChunkUnloadEvent event) {
		if (ConfigOptimize.ClearItemenable != true) {
			return;
		}
		Chunk chunk = event.getChunk();
		Iterator<Coord3<Integer, Integer, World>> it = DeathChunk.iterator();
		while (it.hasNext()) {
		    if (Utils.isSameChunk(chunk, it.next())) {
		        it.remove();
		        return;
		    }
		}
		Entity[] entities = chunk.getEntities();
		for (int i = 0; i < entities.length; i++) {
			Entity ent = entities[i];
			if (ent.getType() == EntityType.DROPPED_ITEM) {
				if (ConfigOptimize.ClearItemNoClearItemType.contains(((Item) ent).getType().name()) == false) {
					ent.remove();
				}
			}
		}
	}

	@EventHandler
	public void DeathNoClear(PlayerDeathEvent event) {
		if (ConfigOptimize.ClearItemNoCleatDeath != true) {
			return;
		}
		Player player = event.getEntity();
		Chunk chunk = player.getLocation().getChunk();
		List<Coord3<Integer, Integer, World>> chunks = Utils.getNearbyChunks(chunk);
		DeathChunk.addAll(chunks);
	}

	@EventHandler
	public void TeleportNoClear(PlayerTeleportEvent event) {
		if (ConfigOptimize.ClearItemNoClearTeleport != true) {
			return;
		}
		Player player = event.getPlayer();
		Chunk chunk = player.getLocation().getChunk();
		List<Coord3<Integer, Integer, World>> chunks = Utils.getNearbyChunks(chunk);
		DeathChunk.addAll(chunks);
	}
}
