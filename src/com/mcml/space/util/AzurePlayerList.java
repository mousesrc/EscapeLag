package com.mcml.space.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

/**
 * @author Vlvxingze, SotrForgotten
 */
public class AzurePlayerList implements Listener {
    private static Set<String> names;
    private static List<Player> players;
    
    private static List<JoinReactor> join;
    private static List<QuitReactor> quit;
    
    private AzurePlayerList() {
        names = Collections.synchronizedSet(AzureAPI.newCaseInsensitiveSet()); // access in AsyncPreLoginEvent
        players = Lists.newArrayListWithExpectedSize(Bukkit.getMaxPlayers());
        
        join = Lists.newArrayList();
        quit = Lists.newArrayList();
        
        for (World world : Bukkit.getWorlds()) {
            for (Player each : world.getPlayers()) {
                names.add(each.getName());
                players.add(each);
            }
        }
    }
    
    public static void bind(JavaPlugin plugin) {
        assert players == null && plugin != null;
        
        Bukkit.getPluginManager().registerEvents(new AzurePlayerList(), plugin);
    }
    
    public static void bind(JoinReactor reactor) {
        join.add(reactor);
    }
    
    public static void bind(QuitReactor reactor) {
        quit.add(reactor);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void inc(PlayerJoinEvent evt){
        Player player = evt.getPlayer();
        names.add(player.getName());
        players.add(player);
        
        if (!join.isEmpty()) {
            for (JoinReactor re : join) re.react(evt);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void dec(PlayerQuitEvent evt){
        Player player = evt.getPlayer();
        names.remove(player.getName());
        players.remove(player);
        
        if (!quit.isEmpty()) {
            for (QuitReactor re : quit) re.react(evt);
        }
    }

    public static List<Player> players() {
        return players;
    }
    
    public static boolean contains(Player player) {
        return names.contains(player.getName());
    }
    
    public static boolean contains(String username) {
        return names.contains(username);
    }
    
    public static boolean isEmpty() {
        return players.isEmpty();
    }
    
    public static int size() {
        return players.size();
    }
}
