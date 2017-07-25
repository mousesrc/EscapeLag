package com.mcml.space.config;

import com.mcml.space.util.Configurable;

public abstract class ConfigMain extends Configurable {
    @Node(path = "PluginPrefix")
    public static String messagePrefix = "&3Vlagger";
    
    @Node(path = "AutoUpdate")
    public static boolean AutoUpdate = false;
    
    @Node(path = "internal-version")
    public static String internalVersion = String.valueOf("%BUILD_NUMBER%");
    
    @Node(path = "language")
    public static String lang = "zh_cn";
    
}
