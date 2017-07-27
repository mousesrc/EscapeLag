package com.amd.sensemi.optimize;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import com.amd.sensemi.VLagger;
import com.amd.sensemi.api.AzureAPI;

public class WorldSpawnLimiter implements Listener {

    @EventHandler
    public void WorldSeterLimitor(WorldInitEvent event) {
        World world = event.getWorld();
        FileConfiguration config = AzureAPI.loadOrCreateFile(VLagger.optimizeConfiguration);
        if (config.getBoolean("WorldSpawnLimitor." + world.getName() + ".enable")) {
            world.setMonsterSpawnLimit(config.getInt("WorldSpawnLimitor." + world.getName() + ".PerChunkMonsters"));
            world.setAnimalSpawnLimit(config.getInt("WorldSpawnLimitor." + world.getName() + ".PerChunkAnimals"));
            world.setAmbientSpawnLimit(config.getInt("WorldSpawnLimitor." + world.getName() + ".PerChunkAmbient"));
            VLagger.MainThis.getLogger().info("已为世界 " + world.getName() + " 设定了生物刷新速率~");
        }
    }
}
