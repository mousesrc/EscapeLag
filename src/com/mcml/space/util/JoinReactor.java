package com.mcml.space.util;

import org.bukkit.event.player.PlayerJoinEvent;

public interface JoinReactor {
    void react(PlayerJoinEvent evt);
}
