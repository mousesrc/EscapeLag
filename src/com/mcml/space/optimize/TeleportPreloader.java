package com.mcml.space.optimize;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzureAPI.Coord2D;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import lombok.val;

import static com.mcml.space.config.ConfigOptimize.usePreloader;
import static com.mcml.space.config.ConfigOptimize.halfPreloader;

/**
 * @author SotrForgotten
 */
public class TeleportPreloader implements Listener {
    public static boolean useCache;
    public static Cache<Location, List<Coord2D>> caches;
    protected static boolean pending;
    protected static final boolean invulnerable = VersionLevel.isHigherEquals(Version.MINECRAFT_1_9_R1); // since 1.9
    
    public TeleportPreloader() {
        if (VersionLevel.isHigherThan(Version.MINECRAFT_1_7_R4)) {
            useCache = true; // versions before this appear to be broken
            
            caches = CacheBuilder.newBuilder()
                    .maximumSize(Bukkit.getMaxPlayers() > 256 ? 256 : (Bukkit.getMaxPlayers() < 64 ? 64 : Bukkit.getMaxPlayers()))
                    .expireAfterWrite(5, TimeUnit.MINUTES)
                    .build();
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent evt) throws ExecutionException {
        if (evt.isAsynchronous() || pending || !usePreloader) return;

        val from = evt.getFrom();
        val to = evt.getTo();
        val player = evt.getPlayer();
        if (from.equals(to)) {
            evt.setCancelled(true);
            return;
        }
        if (!canPreload(from, to, player)) {
            return;
        }
        evt.setCancelled(true);

        val world = player.getWorld();

        boolean custom = AzureAPI.customViewDistance(player);
        List<Coord2D> chunks = custom ? collectPreloadChunks(to, player) : (useCache ? caches.get(to, new Callable<List<Coord2D>>() {
            @Override
            public List<Coord2D> call() {
                List<Coord2D> c = collectPreloadChunks(to, player);
                caches.put(to, c);
                return c;
            }
        }) : collectPreloadChunks(to, player));
        
        val fChunks = chunks;
        val total = chunks.size();
        val preChunks = total / 3;
        val secondStage = preChunks * 2;
        
        if (invulnerable) player.setInvulnerable(true);
        Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
            @Override
            public void run() {
                Coord2D coord;
                for (int i = 0; i < preChunks; i++) {
                    coord = fChunks.get(i);
                    world.getChunkAt(coord.getX(), coord.getZ());
                }
            }
        }, 1L);
        Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
            @Override
            public void run() {
                Coord2D coord;
                for (int i = preChunks; i < secondStage; i++) {
                    coord = fChunks.get(i);
                    world.getChunkAt(coord.getX(), coord.getZ());
                }
            }
        }, 3L);
        Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
            @Override
            public void run() {
                Coord2D coord;
                for (int i = secondStage; i < total; i++) {
                    coord = fChunks.get(i);
                    world.getChunkAt(coord.getX(), coord.getZ());
                }
            }
        }, 5L);
        Bukkit.getScheduler().runTaskLater(VLagger.MainThis, new Runnable() {
            @Override
            public void run() {
                if (invulnerable) player.setInvulnerable(false);
                
                pending = true;
                player.teleport(to);
                pending = false;
            }
        }, 7L);
    }
    
    public static boolean canPreload(Location from, Location to, Player player) {
        if (from.getWorld() != to.getWorld()) return true;
        if (equals2D(from, to) || from.distance(to) < AzureAPI.viewDistanceBlock(player)) {
            return false;
        }

        return true;
    }
    
    public static boolean equals2D(Location from, Location to) {
        return from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ();
    }
    
    public static List<Coord2D> collectPreloadChunks(Location loc, Player player) {
        val view = AzureAPI.viewDistanceBlock(player) / (halfPreloader ? 2 : 1);
        int bX, bZ;
        bX = loc.getBlockX();
        bZ = loc.getBlockZ();
        int minX, minZ, maxX, maxZ;
        minX = bX - view;
        minZ = bZ - view;
        maxX = bX + view;
        maxZ = bZ + view;
        
        List<Coord2D> chunks = Lists.newArrayListWithExpectedSize(AzureAPI.viewDistanceChunk(player));
        int cx, cz;
        for (cx = minX; cx <= maxX; cx+=16) {
            for (cz = minZ; cz <= maxZ; cz+=16) {
                chunks.add(AzureAPI.wrapCoord2D(cx, cz));
            }
        }
        
        return chunks;
    }

}