package com.mcml.space.config;

import java.util.List;

import com.google.common.collect.Lists;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Configurable;

public abstract class ConfigOptimize extends Configurable {
    @Node(path = "OverLoadMemoryRestart.enable")
    public static boolean OverLoadMemoryRestartenable = true;
    
    @Node(path = "ChunkUnloader.Interval")
    public static long ChunkUnloaderInterval = 30;
    
    @Node(path = "WaterFlowLimitor.enable")
    public static boolean WaterFlowLimitorenable;
    
    @Node(path = "WaterFlowLimitor.Period")
    public static long WaterFlowLimitorPeriod = 200L;
    
    @Node(path = "AntiRedstone.enable")
    public static boolean AntiRedstoneenable = true;
    
    @Node(path = "AntiRedstone.Interval")
    public static long AntiRedstoneInterval = 200L;
    
    @Node(path = "FireLimitor.enable")
    public static boolean FireLimitorenable = true;
    
    @Node(path = "FireLimitor.Period")
    public static long FireLimitorPeriod = 3000L;
    
    @Node(path = "TimerGc.enable")
    public static boolean timerGC = false;
    
    @Node(path = "TeleportPreLoader.enable")
    public static boolean usePreloader = true;
    
    @Node(path = "TeleportPreLoader.half-mode")
    public static boolean halfPreloader = true;
    
    @Locale
    @Node(path = "HeapClear.Message")
    public static String messageGC = "§e服务器清理内存中... ԅ(¯ㅂ¯ԅ)";
    
    @Node(path = "HeapClear.Period")
    public static long HeapClearPeriod = 600;
    
    @Node(path = "ClearItem.NoClearItemType")
    public static List<String> ClearItemNoClearItemType = Lists.newArrayList();

    @Node(path = "ClearItem.NoCleatDeath")
    public static boolean ClearItemNoCleatDeath = true;
    
    @Node(path = "ClearItem.NoClearTeleport")
    public static boolean ClearItemNoClearTeleport = true;
    
    @Node(path = "NoCrowdedEntity.enable")
    public static boolean NoCrowdedEntityenable = true;
    
    @Node(path = "NoCrowdedEntity.TypeList")
    public static List<String> NoCrowdedEntityTypeList = AzureAPI.newChainStringList().to("ZOMBIE")
                                                                                       .to("SKELETON")
                                                                                       .to("SPIDER")
                                                                                       .to("CREEPER")
                                                                                       .to("SHEEP")
                                                                                       .to("PIG")
                                                                                       .to("CHICKEN");
    
    @Node(path = "NoCrowdedEntity.PerChunkLimit")
    public static int NoCrowdedEntityPerChunkLimit = 30;
    
    @Locale
    @Node(path = "AntiRedstone.Message")
    public static String AntiRedstoneMessage = "§c检测到高频红石在 %location% ，插件已经将其清除，不许玩了！ (╰_╯)#";
    
    @Node(path = "OverLoadMemoryRestart.Percent")
    public static int OverLoadMemoryRestartPercent = 90;
    
    @Node(path = "AntiRedstone.RemoveBlockList")
    public static List<String> AntiRedstoneRemoveBlockList = AzureAPI.newChainStringList().to("REDSTONE_WIRE")
                                                                                           .to("DIODE_BLOCK_ON")
                                                                                           .to("DIODE_BLOCK_OFF")
                                                                                           .to("REDSTONE_TORCH_ON")
                                                                                           .to("REDSTONE_TORCH_OFF")
                                                                                           .to("REDSTONE_BLOCK");
    
    @Node(path = "AutoSave.Interval")
    public static long AutoSaveInterval = 15;
    
    @Locale
    @Node(path = "OverLoadMemoryRestart.WarnMessage")
    public static String OverLoadMemoryRestartWarnMessage = "服务器会在15秒后重启，请玩家不要游戏，耐心等待！ ╮(╯_╰)╭";
    
    @Node(path = "OverLoadMemoryRestart.DelayTime")
    public static int OverLoadMemoryRestartDelayTime = 15;
    
    @Node(path = "OverLoadMemoryRestart.CanCancel")
    public static boolean OverLoadMemoryRestartCanCancel = true;
    
    @Node(path = "AutoSet.enable")
    public static boolean AutoSetenable = true;
    
    @Node(path = "AutoSave.enable")
    public static boolean AutoSaveenable = true;
    
    @Node(path = "ClearItem.enable")
    public static boolean ClearItemenable = true;
    
    @Node(path = "ChunkKeeper.enable")
    public static boolean ChunkKeeperenable = true;
    
    @Node(path = "ChunkUnloader.enable")
    public static boolean chunkUnloader = false;
    
    @Node(path = "chunks.no-spawn-chunks.enable")
    public static boolean noSpawnChunks = true;
    
    @Node(path = "chunks.no-spawn-chunks.exclude-worlds")
    public static List<String> nscExcludeWorlds = AzureAPI.newChainStringList().to("world");
}
