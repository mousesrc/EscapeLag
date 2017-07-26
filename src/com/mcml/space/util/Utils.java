package com.mcml.space.util;

import org.bukkit.Bukkit;

import com.mcml.space.util.VersionLevel.Version;

public class Utils {
	
	public static void RestartServer(){
		if (VersionLevel.isSpigot() | VersionLevel.isHigherEquals(Version.MINECRAFT_1_7_R1)) {
            new org.spigotmc.RestartCommand("restart").execute(Bukkit.getConsoleSender(), null, null);
        } else {
            // handle by lanuch-script
            Bukkit.shutdown();
        }
	}

}
