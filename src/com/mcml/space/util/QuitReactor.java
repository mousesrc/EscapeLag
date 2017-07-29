package com.mcml.space.util;

import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author SotrForgotten
 */
public interface QuitReactor {
    void react(PlayerQuitEvent evt);
}
