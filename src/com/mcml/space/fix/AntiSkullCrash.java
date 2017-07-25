package com.mcml.space.fix;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import static com.mcml.space.config.ConfigFixing.noSkullCrash;

/**
 * @author jiongjionger
 */
public class AntiSkullCrash implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void NoSkullCrash(BlockFromToEvent evt) {
        if (noSkullCrash) {
            if (evt.getToBlock().getType() == Material.SKULL && VersionLevel.isLowerThan(Version.MINECRAFT_1_9_R1)) {
                evt.setCancelled(true);
            }
        }
    }
}
