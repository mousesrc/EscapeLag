package com.amd.sensemi.config;

import com.amd.sensemi.api.Configurable;

public abstract class Setting extends Configurable {
    @Node(path = "PluginPrefix")
    public static String PluginPrefix = "&3Vlagger";
    
    @Node(path = "AutoUpdate")
    public static boolean AutoUpdate = false;
    
    @Node(path = "internal-version")
    public static String internalVersion = String.valueOf("%BUILD_NUMBER%");
    
    @Node(path = "language")
    public static String lang = "zh_cn";
    
}
