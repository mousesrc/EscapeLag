package com.amd.sensemi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.amd.sensemi.config.Patch;
import com.amd.sensemi.api.AzureAPI;
import com.amd.sensemi.api.AzurePlayerList;
import com.amd.sensemi.api.Configurable;
import com.amd.sensemi.api.NetWorker;
import com.amd.sensemi.api.Ticker;
import com.amd.sensemi.api.VersionLevel;
import com.amd.sensemi.api.VersionLevel.Version;
import com.amd.sensemi.config.Function;
import com.amd.sensemi.config.Optimize;
import com.amd.sensemi.config.Setting;
import com.amd.sensemi.function.AntiSpam;
import com.amd.sensemi.function.AutoUpdateCheck;
import com.amd.sensemi.function.BlockCommander;
import com.amd.sensemi.function.ExplosionController;
import com.amd.sensemi.function.FarmProtecter;
import com.amd.sensemi.function.RespawnAction;
import com.amd.sensemi.function.SpawnerController;
import com.amd.sensemi.optimize.AntiRedstone;
import com.amd.sensemi.optimize.AutoSave;
import com.amd.sensemi.optimize.ChunkKeeper;
import com.amd.sensemi.optimize.ChunkUnloader;
import com.amd.sensemi.optimize.EmptyRestart;
import com.amd.sensemi.optimize.FireLimitor;
import com.amd.sensemi.optimize.ItemClear;
import com.amd.sensemi.optimize.NoCrowdEntity;
import com.amd.sensemi.optimize.NoSpawnChunks;
import com.amd.sensemi.optimize.OverLoadMemoryRestart;
import com.amd.sensemi.optimize.TeleportPreloader;
import com.amd.sensemi.optimize.TimerGarbageCollect;
import com.amd.sensemi.optimize.WaterFlowLimitor;
import com.amd.sensemi.patch.AntiBedExplode;
import com.amd.sensemi.patch.AntiBoneBug;
import com.amd.sensemi.patch.AntiCrashSign;
import com.amd.sensemi.patch.AntiDoorInfItem;
import com.amd.sensemi.patch.AntiDupeDropItem;
import com.amd.sensemi.patch.AntiFakeDeath;
import com.amd.sensemi.patch.AntiInfItem;
import com.amd.sensemi.patch.AntiInfRail;
import com.amd.sensemi.patch.AntiNetherHopperInfItem;
import com.amd.sensemi.patch.AntiPortalInfItem;
import com.amd.sensemi.patch.FixDupeLogin;
import com.amd.sensemi.patch.FixSkullCrash;
import com.amd.sensemi.patch.RPGItemPatch;

public class VLagger extends JavaPlugin implements Listener {

    public static VLagger MainThis;
    public static File optimizeConfiguration;
    public static File AntiBugConfigFile;
    private static File PluginMainConfigFile;
    public static File functionConfiguation;
    public static File PluginFile;

    @Override
    public void onEnable() {
        MainThis = this;
        Ticker.bind(MainThis);
        
        optimizeConfiguration = new File(this.getDataFolder(), "ClearLagConfig.yml");
        AntiBugConfigFile = new File(this.getDataFolder(), "AntiBugConfig.yml");
        PluginMainConfigFile = new File(this.getDataFolder(), "PluginMainConfig.yml");
        functionConfiguation = new File(this.getDataFolder(), "DoEventConfig.yml");
        PluginFile = this.getFile();
        LoadConfig();
        
        AzureAPI.setPrefix(Setting.PluginPrefix + ChatColor.RESET + " > ");
        AzureAPI.log("VLagger —— 新一代的优化/稳定插件");
        AzureAPI.log("~(@^_^@)~ 玩的开心！~");
        
        AzureAPI.log("开始收集服务器信息中...");
        AzureAPI.log("Version " + getDescription().getVersion() + " is ready for installation");
        AzureAPI.log("Server: " + Bukkit.getServer().getVersion());
        AzureAPI.log("Bukkit: " + Bukkit.getServer().getBukkitVersion());
        AzureAPI.log("Level: " + VersionLevel.get() + "\n");
        
        AzureAPI.log("开始加载插件模块中...");
        
        if (Optimize.AutoSetenable == true) {
            try {
                VLagger.AutoSetServer();
            } catch (IOException | InterruptedException e) {
            }
        }
        Bukkit.getPluginManager().registerEvents(new AntiInfItem(), this);
        Bukkit.getPluginManager().registerEvents(new AntiPortalInfItem(), this);
        Bukkit.getPluginManager().registerEvents(new AntiNetherHopperInfItem(), this);
        Bukkit.getPluginManager().registerEvents(new RPGItemPatch(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkKeeper(), this);
        Bukkit.getPluginManager().registerEvents(new NoCrowdEntity(), this);
        Bukkit.getPluginManager().registerEvents(new AntiCrashSign(), this);
        Bukkit.getPluginManager().registerEvents(new AntiSpam(), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionController.EntityDetector(), this);
        Bukkit.getPluginManager().registerEvents(new AntiRedstone(), this);
        Bukkit.getPluginManager().registerEvents(new ItemClear(), this);
        Bukkit.getPluginManager().registerEvents(new NoSpawnChunks(), this);
        Bukkit.getPluginManager().registerEvents(new AntiInfRail(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSave(), this);
        Bukkit.getPluginManager().registerEvents(new FixDupeLogin(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerController(), this);
        Bukkit.getPluginManager().registerEvents(new AntiDupeDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new AntiDoorInfItem(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportPreloader(), this);
        Bukkit.getPluginManager().registerEvents(new AntiBedExplode(), this);
        Bukkit.getPluginManager().registerEvents(new BlockCommander(), this);
        Bukkit.getPluginManager().registerEvents(new WaterFlowLimitor(), this);
        Bukkit.getPluginManager().registerEvents(new FireLimitor(), this);
        Bukkit.getPluginManager().registerEvents(new AutoUpdateCheck(), this);
        Bukkit.getPluginManager().registerEvents(new FarmProtecter(), this);
        Bukkit.getPluginManager().registerEvents(new AntiBoneBug(), this);
        if (VersionLevel.isSpigot()) {
            Bukkit.getPluginManager().registerEvents(new RespawnAction(), this);
        }
        if (Function.emptyRestart) {
            if (Function.emptyRestartHookSpigot) {
                if (VersionLevel.isSpigot()) Bukkit.getPluginManager().registerEvents(new EmptyRestart(), this);
            } else {
                Bukkit.getPluginManager().registerEvents(new EmptyRestart(), this);
            }
        }
        if (VersionLevel.isLowerThan(Version.MINECRAFT_1_9_R1)) {
            Bukkit.getPluginManager().registerEvents(new FixSkullCrash(), this);
        }
        if (VersionLevel.isHigherEquals(Version.MINECRAFT_1_8_R2)) {
            Bukkit.getPluginManager().registerEvents(new ExplosionController.BlockDetector(), this);
        }
        
        ChunkKeeper.ChunkKeeperofTask();
        getServer().getScheduler().runTaskTimer(this, new ChunkUnloader(), 0, Optimize.ChunkUnloaderInterval * 20);
        Bukkit.getScheduler().runTaskTimer(this, new OverLoadMemoryRestart(), 1 * 60 * 20, 1 * 60 * 20);
        Bukkit.getScheduler().runTaskTimer(this, new TimerGarbageCollect(), Optimize.HeapClearPeriod * 20, Optimize.HeapClearPeriod * 20);
        Bukkit.getScheduler().runTaskAsynchronously(this, new NetWorker());
        Bukkit.getScheduler().runTaskTimer(this, new AntiFakeDeath(), 7 * 20, 7 * 20);
        
        AzurePlayerList.bind(this);
        
        AzureAPI.log("------加载完毕------");
        AzureAPI.log("乐乐感谢您的使用——有建议务必反馈，QQ1207223090");
        AzureAPI.log("您可以在插件根目录找到本插件的说明文档 说明文档.txt");
        List<String> devs = getDescription().getAuthors();
        AzureAPI.log("|||"+ devs.get(0) +"/VLagger PluginCD小组作品.|||");
        AzureAPI.log("|||" + AzureAPI.contactBetween(devs, 1, ", ") + " 合作开发.|||");
        AzureAPI.log("§a您正在使用VLagger构建号 %BUILD_NUMBER%");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("vlg")) {
            sender.sendMessage("§b------§a§lVLagger - §e版本 " + getDescription().getVersion() + "§b------");
            if (sender.hasPermission("VLagger.admin")) {
                if (args.length == 0) {
                    sender.sendMessage("§c请输入/vlg help 来获取帮助");
                    return true;
                }
                if (args[0].equalsIgnoreCase("updateon")) {
                    FileConfiguration MainConfig = AzureAPI.loadOrCreateFile(PluginMainConfigFile);
                    MainConfig.set("AutoUpdate", true);
                    try {
                        MainConfig.save(PluginMainConfigFile);
                    } catch (IOException ex) {
                    }
                    Setting.AutoUpdate = true;
                    sender.sendMessage("§a§l[VLagger]§b已经成功开启自动更新！");
                }
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage("§e/vlg reload 重载插件");
                    sender.sendMessage("§e/vlg chunkkeeper 查看关于区块保持者的帮助");
                    sender.sendMessage("§e/vlg heap 查阅关于内存清理和分配的内容");
                    sender.sendMessage("§e/vlg autosave 查阅关于自动储存的内容");
                    sender.sendMessage("§e/vlg tpssleep 查阅关于主线程停顿");
                    sender.sendMessage("§e/vlg autoset 查阅关于自动配端");
                    sender.sendMessage("§e/vlg antiattack 查阅关于反压测模块");
                }
                if (args[0].equalsIgnoreCase("antiattack")) {
                    if (args.length == 1) {
                        sender.sendMessage("§a后置参数:");
                        sender.sendMessage("§edownload 下载反压测模块");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("download")) {
                        sender.sendMessage("§e操作开始执行中...");
                        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
                            @Override
                            public void run() {
                                NetWorker.DownloadAntiAttack();
                            }
                        });
                    }
                }
                if (args[0].equalsIgnoreCase("autoset")) {
                    if (args.length == 1) {
                        sender.sendMessage("§a后置参数:");
                        sender.sendMessage("§eset 执行一次配端操作");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("set")) {
                        try {
                            VLagger.AutoSetServer();
                        } catch (IOException | InterruptedException e) {
                        }
                        sender.sendMessage("§a配端完成！重启服务器即可生效！");
                    }
                }
                if (args[0].equalsIgnoreCase("chunkkeeper")) {
                    if (args.length == 1) {
                        sender.sendMessage("§a后置参数:");
                        sender.sendMessage("§elist 查阅以及被保持的区块列表");
                        sender.sendMessage("§eaddthis 将你所处区块加入保持列表");
                        sender.sendMessage("§eremovethis 将你所处区块删除报错列表");
                        sender.sendMessage("§eclear 清空所有区块保持列表");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("list")) {
                        sender.sendMessage("§e目前以及被保存的区块列表:ChunkKeeper.ShouldKeepList");
                    }
                    if (args[1].equalsIgnoreCase("addthis")) {
                        Player p = (Player) sender;
                        Chunk chunk = p.getLocation().getChunk();
                        ChunkKeeper.ShouldKeepList.add(chunk);
                        sender.sendMessage("§e成功将你所在区块加入保持列表");
                    }
                    if (args[1].equalsIgnoreCase("removethis")) {
                        Player p = (Player) sender;
                        Chunk chunk = p.getLocation().getChunk();
                        if (ChunkKeeper.ShouldKeepList.contains(chunk)) {
                            ChunkKeeper.ShouldKeepList.remove(chunk);
                            sender.sendMessage("§e成功将所在区块从保持列表中删除");
                        } else {
                            sender.sendMessage("§e失败将所在区块从保持列表中删除，因为该区块不在保持列表中。");
                        }
                    }
                    if (args[1].equalsIgnoreCase("clear")) {
                        ChunkKeeper.ShouldKeepList.clear();
                        sender.sendMessage("§e已经清空所有在保持列表的区块。");
                    }
                }
                if (args[0].equalsIgnoreCase("heap")) {
                    if (args.length == 1) {
                        sender.sendMessage("§a后置参数:");
                        sender.sendMessage("§elook 查阅内存使用情况");
                        sender.sendMessage("§eclearheap 强制用java回收内存");
                        sender.sendMessage("§ecleartiles 执行一次清理无用tiles");
                        sender.sendMessage("§eclearchunk 执行一次检测清理区块");
                        sender.sendMessage("§eheapshut 执行一次濒临崩溃内存检测");
                        sender.sendMessage("§echunkunloadlog 查阅区块卸载计数器");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("look")) {
                        sender.sendMessage("§e最大内存 §a" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
                        sender.sendMessage("§e剩余内存 §b" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");
                        sender.sendMessage("§e分配内存 §c" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
                    }
                    if (args[1].equalsIgnoreCase("clearheap")) {
                        System.gc();
                        System.runFinalization();
                        sender.sendMessage("§6内存清理完毕！");
                    }
                    if (args[1].equalsIgnoreCase("clearchunk")) {
                        getServer().getScheduler().runTask(this, new ChunkUnloader());
                        sender.sendMessage("§6区块清理完毕！");
                    }
                    if (args[1].equalsIgnoreCase("heapshut")) {
                        getServer().getScheduler().runTask(this, new OverLoadMemoryRestart());
                        sender.sendMessage("§6成功检测一次内存濒临重启！");
                    }
                    if (args[1].equalsIgnoreCase("chunkunloadlog")) {
                        sender.sendMessage("§a截止到目前，插件已经卸载了" + ChunkUnloader.ChunkUnloaderTimes + "个无用区块");
                    }
                }
                if (args[0].equalsIgnoreCase("autosave")) {
                    if (args.length == 1) {
                        sender.sendMessage("§a后置参数:");
                        sender.sendMessage("§esavethis 将自己所在区块存储");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("savethis")) {
                        Player p = (Player) sender;
                        p.getLocation().getChunk().unload(true);
                        p.getLocation().getChunk().load();
                        sender.sendMessage("§e已经尝试储存区块，该操作不安全!");
                    }
                }
                if (args[0].equalsIgnoreCase("tpssleep")) {
                    if (args.length == 1) {
                        sender.sendMessage("§a后置参数:");
                        sender.sendMessage("§esleep <ms> 停顿主线程毫秒");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("sleep")) {
                        sender.sendMessage("§e成功强制停顿了线程" + args[2] + "毫秒");
                        try {
                            Thread.sleep(Long.parseLong(args[2]));
                        } catch (Exception ex) {
                            sender.sendMessage("§c警告，出现错误!" + ex.toString());
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    LoadConfig();
                    sender.sendMessage("§a§l[VLagger]配置已经成功重载！");
                    return true;
                }
            } else {
                sender.sendMessage("§a§l[VLagger]§4抱歉！您没有足够的权限！");
            }
            return true;
        }
        return false;
    }
    
    private void LoadConfig(){
    	this.saveResource("说明文档.txt", true);
    	
    	try {
            Configurable.restoreNodes(PluginMainConfigFile, Setting.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    	
    	try {
            Configurable.restoreNodes(optimizeConfiguration, Optimize.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        
        try {
            Configurable.restoreNodes(AntiBugConfigFile, Patch.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        
        try {
            Configurable.restoreNodes(functionConfiguation, Function.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
	private void OldLoadConfig() {
    	this.saveResource("说明文档.txt", true);
        FileConfiguration MainConfig = AzureAPI.loadOrCreateFile(PluginMainConfigFile);
        if (MainConfig.getInt("Version") < 272) {
            MainConfig.set("Version", 272);
            MainConfig.set("PluginPrefix", "§a§l[Vlagger]");
            MainConfig.set("AutoUpdate", false);
        }
        try {
            MainConfig.save(PluginMainConfigFile);
        } catch (IOException ex) {
        }

        FileConfiguration ClearLagConfig = AzureAPI.loadOrCreateFile(optimizeConfiguration);
        if (ClearLagConfig.getInt("Version") < 272) {
            ClearLagConfig.set("Version", 272);
            ClearLagConfig.set("AutoSet.enable", true);
            ClearLagConfig.set("AutoSave.enable", true);
            ClearLagConfig.set("AutoSave.Interval", 15);
            ClearLagConfig.set("ClearItem.enable", true);
            ClearLagConfig.set("ClearItem.NoCleatDeath", true);
            ClearLagConfig.set("ClearItem.NoClearTeleport", true);
            List<String> MeterialList = new ArrayList<String>();
            MeterialList.add("DIAMOND");
            MeterialList.add("DIAMOND_SWORD");
            ClearLagConfig.set("ClearItem.NoClearItemType", MeterialList);
            ClearLagConfig.set("NooneRestart.enable", true);
            ClearLagConfig.set("ChunkKeeper.enable", true);
            ClearLagConfig.set("ChunkUnloader.enable", true);
            ClearLagConfig.set("NoCrowdedEntity.enable", true);
            ClearLagConfig.set("NoCrowdedEntity.PerChunkLimit", 30);
            List<String> entt = new ArrayList<String>();
            entt.add("ZOMBIE");
            entt.add("SKELETON");
            entt.add("SPIDER");
            entt.add("CREEPER");
            entt.add("SHEEP");
            entt.add("PIG");
            entt.add("CHICKEN");
            ClearLagConfig.set("NoCrowdedEntity.TypeList", entt);
            ClearLagConfig.set("TilesClear.enable", true);
            ClearLagConfig.set("TilesClear.Interval", 360);
            ClearLagConfig.set("NoExplode.enable", true);
            ClearLagConfig.set("NoExplode.Type", "NoBlockBreak");
            ClearLagConfig.set("AntiRedstone.enable", true);
            ClearLagConfig.set("AntiRedstone.Interval", 500);
            ClearLagConfig.set("AntiRedstone.Message", "§c检测到高频红石在 %location% ，插件已经将其清除，不许玩了！ (╰_╯)#");
            ClearLagConfig.set("TilesClear.Message", "§e服务器清理Tiles完毕 ~(@^_^@)~");
            ClearLagConfig.set("ChunkUnloader.Interval", 30);
            ClearLagConfig.set("HeapClear.enable", false);
            ClearLagConfig.set("HeapClear.Period", 600);
            ClearLagConfig.set("HeapClear.Message", "§e服务器清理内存中... ԅ(¯ㅂ¯ԅ)");
            ClearLagConfig.set("TeleportPreLoader.enable", true);
            ClearLagConfig.set("WaterFlowLimitor.enable", true);
            ClearLagConfig.set("WaterFlowLimitor.Period", 200L);
            ClearLagConfig.set("FireLimitor.enable", true);
            ClearLagConfig.set("FireLimitor.Period", 3000L);
            ClearLagConfig.set("WorldSpawnLimitor.worldname.enable", true);
            ClearLagConfig.set("WorldSpawnLimitor.worldname.PerChunkMonsters", 3);
            ClearLagConfig.set("WorldSpawnLimitor.worldname.PerChunkAnimals", 3);
            ClearLagConfig.set("WorldSpawnLimitor.worldname.PerChunkAmbient", 10);
        }
        if(ClearLagConfig.getInt("Version") < 278){
            ClearLagConfig.set("Version", 278);
            List<String> mas = new ArrayList<String>();
            mas.add("REDSTONE_WIRE");
            mas.add("DIODE_BLOCK_ON");
            mas.add("DIODE_BLOCK_OFF");
            mas.add("REDSTONE_TORCH_ON");
            mas.add("REDSTONE_TORCH_OFF");
            mas.add("REDSTONE_BLOCK");
            ClearLagConfig.set("AntiRedstone.RemoveBlockList", mas);
        }
        try {
            ClearLagConfig.save(optimizeConfiguration);
        } catch (IOException ex) {
        }
        

        FileConfiguration NoBugConfig = AzureAPI.loadOrCreateFile(AntiBugConfigFile);
        if (NoBugConfig.getInt("Version") < 272) {
            NoBugConfig.set("Version", 272);
            NoBugConfig.set("AntiInfItem.enable", true);
            NoBugConfig.set("AntiPortalInfItem.enable", true);
            NoBugConfig.set("AntiNetherHopperInfItem.enable", true);
            NoBugConfig.set("AntiRPGITEM.enable", true);
            NoBugConfig.set("AntiCrashSign.enable", true);
            NoBugConfig.set("AntiSkullCrash.enable", true);
            NoBugConfig.set("NoDoubleOnline.enable", true);
            NoBugConfig.set("NoDoubleOnline.KickMessage", "抱歉，服务器中您已经在线了。ԅ(¯ㅂ¯ԅ)");
            NoBugConfig.set("AntiDoorInfItem.enable", true);
            NoBugConfig.set("AntiCheatBook.enable", true);
            NoBugConfig.set("AntiCheatBook.WarnMessage", "§c严禁利用超级书Bug！");
            NoBugConfig.set("AntiBedExplode.enable", true);
            NoBugConfig.set("AntiBreakUseingChest.enable", true);
            NoBugConfig.set("AntiInfRail.enable", true);
        }
        if(NoBugConfig.getInt("Version") < 290){
        	NoBugConfig.set("Version", 290);
        	NoBugConfig.set("AntiDupeDropItem.enable", true);
        }
        try {
            NoBugConfig.save(AntiBugConfigFile);
        } catch (IOException ex) {
        }
        FileConfiguration EventConfig = AzureAPI.loadOrCreateFile(functionConfiguation);
        if (EventConfig.getInt("Version") < 272) {
            EventConfig.set("Version", 272);
            EventConfig.set("AntiSpam.enable", true);
            EventConfig.set("AntiSpam.Period.Period", 2F);
            EventConfig.set("AntiSpam.Period.WarnMessage", "§c请慢点说话，别激动嘛！ _(:з」∠)_");
            List<String> dirty = new ArrayList<String>();
            dirty.add("操你妈");
            dirty.add("妈逼");
            dirty.add("SB");
            dirty.add("弱智");
            dirty.add("智障");
            dirty.add("杂种");
            dirty.add("狗娘");
            EventConfig.set("AntiSpam.Dirty.List", dirty);
            EventConfig.set("AntiSpam.Dirty.WarnMessage", "§c什么事情激动得你都想骂人啦？");
            EventConfig.set("NoEggChangeSpawner.TipMessage", "§c抱歉，禁止使用刷怪蛋修改刷怪笼");
            EventConfig.set("NoEggChangeSpawner.enable", "§c抱歉，禁止使用刷怪蛋修改刷怪笼");
            EventConfig.set("BlockCommander.enable", false);
            EventConfig.set("BlockCommander.List.NoSpawnWorld./spawn", true);
            EventConfig.set("BlockCommander.List.NoSpawnWorld./spawn.Message", "想在这个世界回城？没门！");
            EventConfig.set("BlockCommander.List.worldname./back", true);
            EventConfig.set("BlockCommander.List.worldname./back.Message", "不好使，不可以用back!");
            EventConfig.set("BlockCommander.List.worldname./tpa", true);
            EventConfig.set("BlockCommander.List.worldname./tpa.Message", "WIFI没信号，不能传送。");
            EventConfig.set("AutoRespawn.enable", true);
            EventConfig.set("AutoRespawn.RespawnTitle.enable", true);
            EventConfig.set("AutoRespawn.RespawnTitle.MainMessage", "§e你死了！");
            EventConfig.set("AutoRespawn.RespawnTitle.MiniMessage", "§c已为您自动复活！");
        }
        try {
            EventConfig.save(functionConfiguation);
        } catch (IOException ex) {
        }
        
        try {
            Configurable.restoreNodes(optimizeConfiguration, Optimize.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        
        try {
            Configurable.restoreNodes(AntiBugConfigFile, Patch.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        
        try {
            Configurable.restoreNodes(functionConfiguation, Function.class);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void AutoSetServer() throws IOException, InterruptedException {
        long heapmb = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        File BukkitFile = new File("bukkit.yml");
        if (BukkitFile.exists()) {
            FileConfiguration bukkit = AzureAPI.loadOrCreateFile(BukkitFile);
            File backupBukkitFile = new File("backup_bukkit.yml");
            if (backupBukkitFile.exists() == false) {
                backupBukkitFile.createNewFile();
                bukkit.save(backupBukkitFile);
            }
            if (heapmb <= 6000) {
                bukkit.set("chunk-gc.period-in-ticks", 300);
            } else {
                bukkit.set("chunk-gc.period-in-ticks", 500);
            }
            bukkit.set("chunk-gc.load-threshold", 400);
            if (heapmb <= 6000) {
                bukkit.set("ticks-per.monster-spawns", 3);
            }
            bukkit.set("VLagger.Changed", "如果Config的AutoSet开启，该参数会被改变。");
            bukkit.save(BukkitFile);
        }
        File SpigotFile = new File("spigot.yml");
        if (SpigotFile.exists()) {
            FileConfiguration spigot = AzureAPI.loadOrCreateFile(SpigotFile);
            File backupSpigotFile = new File("backup_spigot.yml");
            if (backupSpigotFile.exists() == false) {
                backupSpigotFile.createNewFile();
                spigot.save(backupSpigotFile);
            }
            if (heapmb <= 2000) {
                spigot.set("settings.save-user-cache-on-stop-only", true);
            }
            if (heapmb >= 6000) {
                spigot.set("settings.user-cache-size", 5000);
            }
            if (heapmb >= 10000) {
                spigot.set("world-settings.default.view-distance", 4);
            } else if (heapmb >= 6000) {
                spigot.set("world-settings.default.view-distance", 3);
            } else {
                spigot.set("world-settings.default.view-distance", 2);
            }
            if (heapmb <= 4000) {
                spigot.set("world-settings.default.chunks-per-tick", 150);
            } else {
                spigot.set("world-settings.default.chunks-per-tick", 350);
            }
            if (heapmb <= 4000) {
                spigot.set("world-settings.default.max-tick-time.tile", 10);
                spigot.set("world-settings.default.max-tick-time.entity", 20);
            } else {
                spigot.set("world-settings.default.max-tick-time.tile", 20);
                spigot.set("world-settings.default.max-tick-time.entity", 30);
            }
            spigot.set("world-settings.default.entity-activation-range.animals", 12);
            spigot.set("world-settings.default.entity-activation-range.monsters", 24);
            spigot.set("world-settings.default.entity-activation-range.misc", 2);
            spigot.set("world-settings.default.entity-tracking-range.other", 48);
            spigot.set("world-settings.default.random-light-updates", false);
            if (heapmb <= 4000) {
                spigot.set("world-settings.default.save-structure-info", false);
            }
            spigot.set("world-settings.default.max-entity-collisions", 2);
            spigot.set("world-settings.default.max-tnt-per-tick", 20);
            spigot.set("VLagger.Changed", "如果Config的AutoSet开启，该参数会被改变。");
            spigot.save(SpigotFile);
        }
        File PaperFile = new File("paper.yml");
        if (PaperFile.exists()) {
            FileConfiguration paper = AzureAPI.loadOrCreateFile(PaperFile);
            File backupPaperFile = new File("backup_paper.yml");
            if (backupPaperFile.exists() == false) {
                backupPaperFile.createNewFile();
                paper.save(backupPaperFile);
            }
            paper.set("world-settings.default.keep-spawn-loaded", false);
            paper.set("world-settings.default.optimize-explosions", true);
            paper.set("world-settings.default.fast-drain.lava", true);
            paper.set("world-settings.default.fast-drain.water", true);
            paper.set("world-settings.default.use-async-lighting", true);
            if (heapmb <= 6000) {
                paper.set("world-settings.default.tick-next-tick-list-cap", 8000);
            }
            paper.set("world-settings.default.tick-next-tick-list-cap-ignores-redstone", true);
            paper.save(PaperFile);
        }
        if (BukkitFile.exists()) {
            FileConfiguration bukkit = AzureAPI.loadOrCreateFile(BukkitFile);
            if (bukkit.getInt("VLagger.SetStep") == 1) {
                bukkit.set("VLagger.SetStep", 2);
                try {
                    bukkit.save(BukkitFile);
                } catch (IOException ex) {
                }
            }
            if (bukkit.getInt("VLagger.SetStep") == 0) {
                bukkit.set("VLagger.SetStep", 1);
                bukkit.save(BukkitFile);
                AzureAPI.log("成功改动服务器配端，正在重启来启用它.");
                Bukkit.shutdown();
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("VLagger —— 已经停止使用");
        getLogger().info("感谢您的使用——乐乐");
    }
}
