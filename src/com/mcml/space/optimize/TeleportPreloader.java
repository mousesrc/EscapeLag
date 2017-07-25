package com.mcml.space.optimize;

import org.bukkit.event.Listener;

/**
 * @author SotrForgotten
 */
public class TeleportPreloader implements Listener {
    /*public static final Cache<Location, List<Coord2D>> caches = CacheBuilder.newBuilder().maximumSize(Bukkit.getMaxPlayers() > 256 ? 256 : (Bukkit.getMaxPlayers() < 64 ? 64 : Bukkit.getMaxPlayers())).expireAfterWrite(5, TimeUnit.MINUTES).build();
    protected volatile static boolean pending;
    protected static final boolean invulnerable = VersionLevel.isHigherEquals(Version.MINECRAFT_1_9_R1); // since 1.9
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent evt) throws ExecutionException {
        if (evt.isAsynchronous() || pending || !ConfigOptimize.TeleportPreLoaderenable) return;

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
        List<Coord2D> chunks = custom ? collectPreloadChunks(to, player) : caches.get(to, new Callable<List<Coord2D>>() {
            @Override
            public List<Coord2D> call() {
                return collectPreloadChunks(to, player);
            }
        });
        if (chunks == null) {
            chunks = collectPreloadChunks(to, player);
            caches.put(to, chunks);
        }
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
        val view = AzureAPI.viewDistanceBlock(player);
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
    }*/
	
	/*报错内容：（1.7.10 KC服务端报错）
	 * [19:43:42] [Server thread/ERROR]: Error occurred while enabling VLagger v2.8.3-185 (Is it up to date?)
	java.lang.NoSuchMethodError: guava10.com.google.common.cache.CacheBuilder.maximumSize(J)Lguava10/com/google/common/cache/CacheBuilder;
at com.mcml.space.optimize.TeleportPreloader.<clinit>(TeleportPreloader.java:32) ~[?:?]
at com.mcml.space.core.VLagger.onEnable(VLagger.java:152) ~[?:?]
at org.bukkit.plugin.java.JavaPlugin.setEnabled(JavaPlugin.java:316) ~[JavaPlugin.class:git-Cauldron-MCPC-Plus-1.7.10-1.1199.01.149]
at org.bukkit.plugin.java.JavaPluginLoader.enablePlugin(JavaPluginLoader.java:360) [JavaPluginLoader.class:git-Cauldron-MCPC-Plus-1.7.10-1.1199.01.149]
at org.bukkit.plugin.SimplePluginManager.enablePlugin(SimplePluginManager.java:405) [SimplePluginManager.class:git-Cauldron-MCPC-Plus-1.7.10-1.1199.01.149]
at org.bukkit.craftbukkit.v1_7_R4.CraftServer.loadPlugin(CraftServer.java:413) [CraftServer.class:git-Cauldron-MCPC-Plus-1.7.10-1.1199.01.149]
at org.bukkit.craftbukkit.v1_7_R4.CraftServer.enablePlugins(CraftServer.java:347) [CraftServer.class:git-Cauldron-MCPC-Plus-1.7.10-1.1199.01.149]
at net.minecraft.server.MinecraftServer.func_71243_i(MinecraftServer.java:504) [MinecraftServer.class:?]
at net.minecraft.server.MinecraftServer.func_71222_d(MinecraftServer.java:478) [MinecraftServer.class:?]
at net.minecraft.server.MinecraftServer.func_71247_a(MinecraftServer.java:438) [MinecraftServer.class:?]
at net.minecraft.server.dedicated.DedicatedServer.func_71197_b(DedicatedServer.java:328) [lt.class:?]
at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:624) [MinecraftServer.class:?]
at java.lang.Thread.run(Unknown Source) [?:1.7.0_80]*/
}
