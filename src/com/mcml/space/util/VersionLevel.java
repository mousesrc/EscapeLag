package com.mcml.space.util;

import org.bukkit.Bukkit;

/**
 * @author SotrForgotten
 */
public class VersionLevel {
    private final static Version level = check();
    private static boolean paper;
    private static boolean spigot;
    private static boolean forge;
    
    public static final Version get() {
        return level;
    }
    
    private static Version check() {
        String version = Bukkit.getServer().getVersion();
        checkType(version);
        
        if (version.contains("1.12")) {
            return Version.MINECRAFT_1_12_R1;
        }
        
        if (version.contains("1.11")) {
            return Version.MINECRAFT_1_11_R1;
        }
        
        if (version.contains("1.10")) {
            return Version.MINECRAFT_1_10_R1;
        }
        
        if (version.contains("1.9.4")) {
            return Version.MINECRAFT_1_9_R2;
        }
        
        if (version.contains("1.9")) {
            return Version.MINECRAFT_1_9_R1;
        }
        
        if (version.contains("1.8.8")) {
            return Version.MINECRAFT_1_8_R3;
        }
        
        if (version.contains("1.8.3")) {
            return Version.MINECRAFT_1_8_R2;
        }
        
        if (version.contains("1.8")) {
            return Version.MINECRAFT_1_8_R1;
        }
        
        if (version.contains("1.7.10")) {
            return Version.MINECRAFT_1_7_R4;
        }
        
        if (version.contains("1.7.9")) {
            return Version.MINECRAFT_1_7_R3;
        }
        
        if (version.contains("1.7.5")) {
            return Version.MINECRAFT_1_7_R2;
        }
        
        if (version.contains("1.7")) {
            return Version.MINECRAFT_1_7_R1;
        }
        
        if (version.contains("1.6.4")) {
            return Version.MINECRAFT_1_6_R3;
        }
        
        if (version.contains("1.6.2")) {
            return Version.MINECRAFT_1_6_R2;
        }
        
        if (version.contains("1.6.1")) {
            return Version.MINECRAFT_1_6_R1;
        }
        
        if (version.contains("1.5.2")) {
            return Version.MINECRAFT_1_5_R3;
        }
        
        if (version.contains("1.5.1")) {
            return Version.MINECRAFT_1_5_R2;
        }
        
        if (version.contains("1.5")) {
            return Version.MINECRAFT_1_5_R1;
        }
        
        if (version.contains("1.4.7")) {
            return Version.MINECRAFT_1_4_R1;
        }
        
        if (version.contains("1.4.6")) {
            return Version.MINECRAFT_1_4_6;
        }
        
        AzureAPI.warn("Cannot capture server version (unsupport?)");
        return Version.UNKNOWN;
    }
    
    public static boolean isPaper() {
        return paper;
    }
    
    public static boolean isSpigot() {
        return spigot;
    }
    
    public static boolean isForge() {
        return forge;
    }
    
    private static void checkType(String bukkitVersion) {
        bukkitVersion = bukkitVersion.toLowerCase();
        boolean thermos = bukkitVersion.contains("thermos");
        forge = bukkitVersion.contains("cauldron") || bukkitVersion.contains("mcpc") || thermos;
        paper = bukkitVersion.contains("paper") || bukkitVersion.contains("taco") || bukkitVersion.contains("torch");
        spigot = (paper ? true : bukkitVersion.contains("spigot") || bukkitVersion.contains("hose") || bukkitVersion.contains("glowstone")) || thermos;
    }
    
    public static boolean isLowerThan(Version other) {
        return level.ordinal() > other.ordinal();
    }
    
    public static boolean isLowerEquals(Version other) {
        return level.ordinal() >= other.ordinal();
    }
    
    public static boolean isHigherThan(Version other) {
        return level.ordinal() < other.ordinal();
    }
    
    public static boolean isHigherEquals(Version other) {
        return level.ordinal() <= other.ordinal();
    }
    
    public static boolean equals(Version other) {
        return level == other;
    }
    
    // The order is important!
    public enum Version {
        MINECRAFT_1_12_R1,
        MINECRAFT_1_11_R1,
        MINECRAFT_1_10_R1,
        
        MINECRAFT_1_9_R2,
        MINECRAFT_1_9_R1,
        
        MINECRAFT_1_8_R3,
        MINECRAFT_1_8_R2,
        MINECRAFT_1_8_R1,
        
        MINECRAFT_1_7_R4,
        MINECRAFT_1_7_R3,
        MINECRAFT_1_7_R2,
        MINECRAFT_1_7_R1,
        
        MINECRAFT_1_6_R3,
        MINECRAFT_1_6_R2,
        MINECRAFT_1_6_R1,
        
        MINECRAFT_1_5_R3,
        MINECRAFT_1_5_R2,
        MINECRAFT_1_5_R1,
        
        MINECRAFT_1_4_R1,
        MINECRAFT_1_4_6,
        
        UNKNOWN;
    }
}