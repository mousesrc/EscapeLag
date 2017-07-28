package com.mcml.space.patch;

import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType.Play;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Sets;
import com.mcml.space.config.ConfigPatch;
import com.mcml.space.core.VLagger;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

public class RecipeDupePatch implements Listener {
    public static void init(JavaPlugin plugin) {
        if (!VersionLevel.isHigherEquals(Version.MINECRAFT_1_12_R1)) return;
        
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Bukkit.getPluginManager().registerEvents(new RecipeDupePatch(), plugin);
        } else {
            AzureAPI.warn("检测到您正使用 1.12 版本的服务端, 但未安装 ProtocolLib 前置插件");
            AzureAPI.warn("这将导致某些重要的防护功能不可用, 强烈建议您安装 ProtocolLib 并重启服务端");
        }
        
        Bukkit.getPluginManager().registerEvents(new DupeLoginPatch(), plugin);
        AzureAPI.log("自动合成修复模块已启用");
    }
    
    public final Set<Player> keepers;
    
    public RecipeDupePatch() {
        keepers = Sets.newHashSet();
        
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(VLagger.MainThis, Play.Client.AUTO_RECIPE) {
            @Override
            public void onPacketReceiving(PacketEvent evt) {
                if (!ConfigPatch.autoRecipePatch) return;
                keepers.add(evt.getPlayer());
            }
        });
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(PlayerPickupItemEvent evt) {
        if (!ConfigPatch.autoRecipePatch) return;
        if (keepers.contains(evt.getPlayer())) evt.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClose(InventoryCloseEvent evt) {
        if (!ConfigPatch.autoRecipePatch) return;
        keepers.remove(evt.getPlayer());
    }
    
}