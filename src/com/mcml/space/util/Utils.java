package com.mcml.space.util;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;
import com.mcml.space.util.AzureAPI.Coord;
import com.mcml.space.util.AzureAPI.Coord3;

public class Utils {
	
	public static List<Coord<Integer, Integer>> getShouldUseChunks(Location loc, Player player){
		List<Coord<Integer, Integer>> chunks = Lists.newArrayList();
		int vd = AzureAPI.viewDistance(player);
		int hvd = vd / 2;
        for(int i = loc.getChunk().getX() - hvd; i < loc.getChunk().getX() + hvd; i++){
            for(int ii = loc.getChunk().getZ() - hvd; ii<loc.getChunk().getZ() + hvd; ii++){
                chunks.add(AzureAPI.wrapCoord(i, ii));
            }
        }
        return chunks;
	}

	/**
	 * @param chunk source
	 * @return nearby 9 chunks
	 */
	public static List<Coord3<Integer, Integer, World>> getNearbyChunks(Chunk chunk){
	    return getNearbyChunks(chunk.getWorld(), chunk.getX(), chunk.getZ());
	}

	/**
	 * @param world search world
	 * @param x source x
	 * @param z source z
	 * @return nearby 9 chunks
	 */
	public static List<Coord3<Integer, Integer, World>> getNearbyChunks(World world, int x, int z) {
	    List<Coord3<Integer, Integer, World>> chunks = Lists.newArrayListWithExpectedSize(9);
	    for(int cx = x - 1; cx < x + 1; cx++){
	        for(int cz = z - 1; cz < z + 1; cz++){
	            chunks.add(AzureAPI.wrapCoord(cx, cz, world));
	        }
	    }
	    return chunks;
	}

	public static boolean isSameChunk(Chunk chunk1, Chunk chunk2){
		String c1w = chunk1.getWorld().getName();
		String c2w = chunk2.getWorld().getName();
		int c1x = chunk1.getX();
		int c2x = chunk2.getX();
		int c1z = chunk1.getZ();
		int c2z = chunk2.getZ();
		if(c1w.equals(c2w) && c1x == c2x && c1z == c2z){
			return true;
		}else{
			return false;
		}
	}
	
	@Deprecated
	public static boolean isSameChunk(Chunk chunk1, Coord3<Integer, Integer, World> chunk2){
        String c1w = chunk1.getWorld().getName();
        String c2w = chunk2.getExtra().getName();
        int c1x = chunk1.getX();
        int c2x = chunk2.getKey();
        int c1z = chunk1.getZ();
        int c2z = chunk2.getValue();
        if(c1w.equals(c2w) && c1x == c2x && c1z == c2z){
            return true;
        }else{
            return false;
        }
    }
}
