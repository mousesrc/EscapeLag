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
	
	public static boolean isSameChunk(Chunk chunk1,Chunk chunk2){
		String c1w = chunk1.getWorld().getName();
		String c2w = chunk2.getWorld().getName();
		int c1x = chunk1.getX();
		int c2x = chunk2.getX();
		int c1z = chunk1.getZ();
		int c2z = chunk2.getZ();
		if(c1w.equals(c2w)&c1x == c2x&c1z == c2z){
			return true;
		}else{
			return false;
		}
	}
}
