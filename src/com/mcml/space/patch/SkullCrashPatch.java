package com.mcml.space.patch;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.PluginExtends;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import static com.mcml.space.config.ConfigPatch.noSkullCrash;

/**
 * @author jiongjionger
 */
public class SkullCrashPatch implements Listener, PluginExtends {
    public static void init(JavaPlugin plugin) {
        if (VersionLevel.isHigherEquals(Version.MINECRAFT_1_9_R1)) return;
        
        Bukkit.getPluginManager().registerEvents(new SkullCrashPatch(), plugin);
        AzureAPI.log("头颅修复模块已启用");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void NoSkullCrash(BlockFromToEvent evt) {
        if (noSkullCrash) {
            if (evt.getToBlock().getType() == Material.SKULL) {
                evt.setCancelled(true);
            }
        }
    }
}
