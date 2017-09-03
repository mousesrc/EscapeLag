package com.mcml.space.optimize;

import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.core.EscapeLag;

public class ChunkUnloaderofRunnable implements Runnable {
    public static int ChunkUnloaderTimes;

    @Override
    public void run() {
        if (ConfigOptimize.chunkUnloader == true) {
            List<World> worlds = Bukkit.getWorlds();
            final Iterator<World> it = worlds.iterator();
            int dotick = 0;
            while(it.hasNext()) {
            	dotick = dotick + 5;
            	Bukkit.getScheduler().runTaskLater(EscapeLag.MainThis, new Runnable() {
            		public void run() {
            			World world = it.next();
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
            	}, dotick);
            }
        }
    }
}
