package com.amd.sensemi.patch;

import static com.amd.sensemi.config.Patch.messageCheatBook;
import static com.amd.sensemi.config.Patch.noCheatBook;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import com.amd.sensemi.api.AzureAPI;

import lombok.val;

/**
 * @author SotrForgotten
 */
public class CheatBookBlocker implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBookEdit(PlayerEditBookEvent evt) {
        if (!noCheatBook) return;
        
        val prev = evt.getPreviousBookMeta();
        val meta = evt.getNewBookMeta();
        if (prev.equals(meta)) return;
        
        // Illegally modify lore
        if (prev.hasLore()) {
            if (!meta.hasLore() || !prev.getLore().equals(meta.getLore())) {
                meta.setLore(prev.getLore());
            }
        } else if (meta.hasLore()) {
            meta.setLore(null);
        }
        
        // Illegally modify enchants
        if (prev.hasEnchants()) {
            if (!meta.hasEnchants()) {
                addEnchantFrom(prev, meta);
            } else if (!prev.getLore().equals(meta.getLore())) {
                clearEnchant(meta);
                addEnchantFrom(prev, meta);
            }
        } else if (meta.hasEnchants()) {
            clearEnchant(meta);
        }
        
        // They cannot change title by edit it!
        val title = prev.getTitle();
        if (!title.equals(meta.getTitle())) {
            meta.setTitle(title);
        }
        
        // Book and quill doesn't has a generation!
        if (meta.getGeneration() != null) meta.setGeneration(null);
        
        // Book and quill doesn't has an author!
        if (meta.getAuthor() != null) meta.setAuthor(null);
        
        evt.setNewBookMeta(meta);
        
        AzureAPI.log(evt.getPlayer(), messageCheatBook);
    }
    
    public static BookMeta clearEnchant(BookMeta meta) {
        for (val e : meta.getEnchants().keySet()) {
            meta.removeEnchant(e);
        }
        return meta;
    }
    
    public static BookMeta addEnchantFrom(BookMeta source, BookMeta meta) {
        for (val e : source.getEnchants().entrySet()) {
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }
        return meta;
    }
}