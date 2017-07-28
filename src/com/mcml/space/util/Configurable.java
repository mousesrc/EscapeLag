package com.mcml.space.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.bukkit.configuration.file.FileConfiguration;

import com.mcml.space.util.AzureAPI.Coord;

import lombok.val;

/**
 * @author SotrForgotten
 */
public abstract class Configurable {
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Node {
        String path();
    }
    
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Locale {}
    
    public static void restoreNodes(Coord<File, FileConfiguration> coord, Class<? extends Configurable> clazz) throws IllegalArgumentException, IllegalAccessException, IOException {
        val config = coord.getValue();
        
        for (Field field : clazz.getDeclaredFields()) {
            Node node = field.getAnnotation(Node.class);
            if (node == null) continue;
            field.setAccessible(true);
            
            val def = field.get(null);
            val mod = field.getModifiers();
            if (Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
                val path = node.path();
                val value = config.get(path);
                if (value == null) {
                    config.set(path, def);
                    field.set(null, AzureAPI.colorzine(def)); // for colorzine
                } else {
                    field.set(null, AzureAPI.colorzine(value));
                }
            }
        }
        
        config.save(coord.getKey());
    }
}
