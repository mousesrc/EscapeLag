package com.mcml.space.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.mcml.space.core.VLagger;

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
    
    public static void restoreNodes(File file, Class<? extends Configurable> clazz) throws IllegalArgumentException, IllegalAccessException, IOException {
        assert VLagger.MainThis != null;
        val config = AzureAPI.loadOrCreateFile(file);
        
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
                    field.set(null, colorzine(def)); // for colorzine
                } else {
                    field.set(null, colorzine(value));
                }
            }
        }
        
        config.save(file);
    }
    
    @SuppressWarnings("all")
    public static Object colorzine(Object o) {
        if (o instanceof String) {
            return StringUtils.replaceChars((String) o, '&', '§');
        }
        if (o instanceof List) {
            List list = (List) o;
            for (Object obj : list) {
                if (obj instanceof String) {
                    list.set(list.indexOf(obj), StringUtils.replaceChars((String) obj, '&', '§'));
                }
            }
            return list;
        }
        return o;
    }
}
