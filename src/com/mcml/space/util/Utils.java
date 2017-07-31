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
}
