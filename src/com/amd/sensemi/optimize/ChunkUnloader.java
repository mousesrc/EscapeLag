package com.amd.sensemi.optimize;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import com.amd.sensemi.config.Optimize;

public class ChunkUnloader implements Runnable {
    // TODO inconsistent with chunk-unload-delay
    public static int ChunkUnloaderTimes;

    @Override
    public void run() {
        if (Optimize.chunkUnloader == true) {
            List<World> worlds = Bukkit.getWorlds();
            int ws = worlds.size();
            for (int i = 0; i < ws; i++) {
                World world = worlds.get(i);
                Chunk[] loadedChunks = world.getLoadedChunks();
                int lcl = loadedChunks.length;
                for(int ii=0;ii<lcl;ii++){
                    Chunk chunk = loadedChunks[ii];
                    if(world.isChunkInUse(chunk.getX(),chunk.getZ())==false){
                        if(chunk.isLoaded() == true & ChunkKeeper.ShouldKeepList.contains(chunk)==false){
                            chunk.unload();
                            ChunkUnloaderTimes++;
                        }
                    }
                }
            }
        }
    }
}
