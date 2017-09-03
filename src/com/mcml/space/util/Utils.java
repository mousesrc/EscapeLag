package com.mcml.space.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;
import com.mcml.space.util.AzureAPI.Coord;
import com.mcml.space.util.AzureAPI.Coord3;

public class Utils {
	@SuppressWarnings("all")
	public static ArrayList<Map.Entry<Plugin, Long>> sortMap(Map map) {
		List<Map.Entry<Plugin, Long>> entries = new ArrayList<Map.Entry<Plugin, Long>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Plugin, Long>>() {
			public int compare(Map.Entry<Plugin, Long> obj1, Map.Entry<Plugin, Long> obj2) {
				return (int) (obj2.getValue() - obj1.getValue());
			}
		});
		return (ArrayList<Entry<Plugin, Long>>) entries;
	}

	public static List<Coord<Integer, Integer>> getShouldUseChunks(Location loc) {
		List<Coord<Integer, Integer>> chunks = Lists.newArrayList();
		int vd = Bukkit.getViewDistance();
		int hvd = vd / 2;
		for (int i = loc.getChunk().getX() - hvd; i <= loc.getChunk().getX() + hvd; i++) {
			for (int ii = loc.getChunk().getZ() - hvd; ii <= loc.getChunk().getZ() + hvd; ii++) {
				chunks.add(AzureAPI.wrapCoord(i, ii));
			}
		}
		return chunks;
	}

	/**
	 * @param chunk
	 *            source
	 * @return nearby 9 chunks
	 */
	public static List<Coord3<Integer, Integer, World>> getNearbyChunks(Chunk chunk) {
		return getNearbyChunks(chunk.getWorld(), chunk.getX(), chunk.getZ());
	}

	/**
	 * @param world
	 *            search world
	 * @param x
	 *            source x
	 * @param z
	 *            source z
	 * @return nearby 9 chunks
	 */
	public static List<Coord3<Integer, Integer, World>> getNearbyChunks(World world, int x, int z) {
		List<Coord3<Integer, Integer, World>> chunks = Lists.newArrayListWithExpectedSize(9);
		for (int cx = x - 1; cx < x + 1; cx++) {
			for (int cz = z - 1; cz < z + 1; cz++) {
				chunks.add(AzureAPI.wrapCoord(cx, cz, world));
			}
		}
		return chunks;
	}

	public static boolean isSameChunk(Chunk chunk1, Chunk chunk2) {
		String c1w = chunk1.getWorld().getName();
		String c2w = chunk2.getWorld().getName();
		int c1x = chunk1.getX();
		int c2x = chunk2.getX();
		int c1z = chunk1.getZ();
		int c2z = chunk2.getZ();
		if (c1w.equals(c2w) && c1x == c2x && c1z == c2z) {
			return true;
		} else {
			return false;
		}
	}

	public static String readTxtFile(File file) {
		String read;
		FileReader fileread;
		String readResult = "";
		try {
			fileread = new FileReader(file);
			BufferedReader br = new BufferedReader(fileread);
			try {
				while ((read = br.readLine()) != null) {
					readResult = readResult + read + "/r/n";
				}
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
		}
		return readResult;
	}

	public static void ChangeTxtFileAndSave(String LastFileString, String newStringToWriteIn, File file) {
		// 先读取原有文件内容，然后进行写入操作
		String NewResult = newStringToWriteIn + "/r/n" + LastFileString;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.writeBytes(NewResult);
		} catch (IOException e1) {
		}
		if (raf != null) {
			try {
				raf.close();
			} catch (IOException e2) {
			}
		}
	}
}
