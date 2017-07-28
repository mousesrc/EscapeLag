package com.mcml.space.util;

import org.bukkit.event.player.PlayerQuitEvent;

public interface QuitReactor {
    void react(PlayerQuitEvent evt);
}
