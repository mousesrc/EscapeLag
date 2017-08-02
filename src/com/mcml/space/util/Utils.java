package com.mcml.space.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class Utils {
	
	public static List<Chunk> getShouldUseChunk(Location loc){
		List<Chunk> chunks = new ArrayList<Chunk>();
		int vd = Bukkit.getViewDistance();
		int hvd = vd/2;
		for(int i = loc.getChunk().getX() - hvd;i<loc.getChunk().getX() + hvd;i++){
			for(int ii = loc.getChunk().getZ() - hvd;ii<loc.getChunk().getZ() + hvd;ii++){
				chunks.add(loc.getWorld().getChunkAt(i, ii));
			}
		}
		return chunks;
	}
	
	public static List<Chunk> getnearby9chunks(Chunk chunk){
		List<Chunk> chunks = new ArrayList<Chunk>();
		for(int i = chunk.getX() - 1;i<chunk.getX() + 1;i++){
			for(int ii = chunk.getZ() - 1;ii<chunk.getZ() + 1;ii++){
				chunks.add(chunk.getWorld().getChunkAt(i, ii));
			}
		}
		return chunks;
	}
}
