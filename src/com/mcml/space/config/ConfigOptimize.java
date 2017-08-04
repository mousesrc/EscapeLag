package com.mcml.space.config;

import java.util.List;

import com.mcml.space.util.Configurable;

public abstract class ConfigOptimize extends Configurable {
	@Node(path = "NooneRestart.enable")
	public static boolean emptyRestart = true;

	@Node(path = "NooneRestart.TimeLong")
	public static long emptyRestartDelay = 1200;
	
    @Node(path = "OverLoadMemoryRestart.enable")
    public static boolean OverLoadMemoryRestartenable = true; //用户反馈关闭默认比较好
    
    @Node(path = "ChunkUnloader.Interval")
    public static long ChunkUnloaderInterval = 30;
    
    @Node(path = "WaterFlowLimitor.enable")
    public static boolean WaterFlowLimitorenable = true;
    
    @Node(path = "WaterFlowLimitor.PerChunkTimes")
    public static long WaterFlowLimitorPerChunkTimes = 2;
    
    @Node(path = "AntiRedstone.enable")
    public static boolean AntiRedstoneenable = true;
    
    @Node(path = "AntiRedstone.Times")
    public static long AntiRedstoneTimes = 5;
    
    @Node(path = "FireLimitor.enable")
    public static boolean FireLimitorenable = true;
    
    @Node(path = "FireLimitor.Period")
    public static long FireLimitorPeriod = 3000L;
    
    @Node(path = "TimerGc.enable")
    public static boolean timerGC = false;
    
    @Node(path = "TeleportPreLoader.enable")
    public static boolean TeleportPreLoaderenable = true;
    
    @Locale
    @Node(path = "TimerGc.Message")
    public static String TimerGcMessage = "§e服务器清理内存中... ԅ(¯ㅂ¯ԅ)";
    
    @Node(path = "TimerGc.Period")
    public static long TimerGcPeriod = 600;
    
    @Node(path = "ClearItem.NoClearItemType")
    public static List<String> ClearItemNoClearItemType = Default.ClearItemNoClearItemType();

    @Node(path = "ClearItem.NoCleatDeath")
    public static boolean ClearItemNoCleatDeath = true;
    
    @Node(path = "ClearItem.NoClearTeleport")
    public static boolean ClearItemNoClearTeleport = true;
    
    @Node(path = "NoCrowdedEntity.enable")
    public static boolean NoCrowdedEntityenable = true;
    
    @Node(path = "NoCrowdedEntity.TypeList")
    public static List<String> NoCrowdedEntityTypeList = Default.NoCrowdedEntityTypeList();
    
    @Node(path = "NoCrowdedEntity.PerChunkLimit")
    public static int NoCrowdedEntityPerChunkLimit = 30;
    
    @Locale
    @Node(path = "AntiRedstone.Message")
    public static String AntiRedstoneMessage = "§c检测到高频红石在 %location% ，插件已经将其清除，不许玩了！ (╰_╯)#";
    
    @Node(path = "OverLoadMemoryRestart.Percent")
    public static int OverLoadMemoryRestartPercent = 90;
    
    @Node(path = "AntiRedstone.RemoveBlockList")
    public static List<String> AntiRedstoneRemoveBlockList = Default.AntiRedstoneRemoveBlockList();
    
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
    public static boolean chunkUnloader = true;
    
    @Node(path = "NoSpawnChunks.enable")
    public static boolean noSpawnChunks = true;
}
