package com.mcml.space.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcml.space.config.ConfigFunction;
import com.mcml.space.config.ConfigMain;
import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.config.ConfigPatch;
import com.mcml.space.function.AntiSpam;
import com.mcml.space.function.ExplosionController;
import com.mcml.space.function.FarmProtecter;
import com.mcml.space.function.RespawnAction;
import com.mcml.space.function.SpawnerController;
import com.mcml.space.function.UpgradeNotifier;
import com.mcml.space.monitor.MonitorEnabler;
import com.mcml.space.monitor.MonitorRecord;
import com.mcml.space.monitor.MonitorUtils;
import com.mcml.space.optimize.AntiRedstone;
import com.mcml.space.optimize.AutoSave;
import com.mcml.space.optimize.ChunkKeeper;
import com.mcml.space.optimize.ChunkUnloader;
import com.mcml.space.optimize.EmptyRestart;
import com.mcml.space.optimize.FireLimitor;
import com.mcml.space.optimize.ItemClear;
import com.mcml.space.optimize.NoCrowdEntity;
import com.mcml.space.optimize.NoSpawnChunks;
import com.mcml.space.optimize.OverloadRestart;
import com.mcml.space.optimize.TeleportPreLoader;
import com.mcml.space.optimize.TimerGarbageCollect;
import com.mcml.space.optimize.WaterFlowLimitor;
import com.mcml.space.patch.AntiBedExplode;
import com.mcml.space.patch.AntiCrashSign;
import com.mcml.space.patch.AntiInfSuagr;
import com.mcml.space.patch.AntiDupeDropItem;
import com.mcml.space.patch.AntiFakeDeath;
import com.mcml.space.patch.AntiInfItem;
import com.mcml.space.patch.AntiInfRail;
import com.mcml.space.patch.AntiLongStringCrash;
import com.mcml.space.patch.AntiNetherHopperInfItem;
import com.mcml.space.patch.AntiPortalInfItem;
import com.mcml.space.patch.BonemealDupePatch;
import com.mcml.space.patch.CheatBookBlocker;
import com.mcml.space.patch.DupeLoginPatch;
import com.mcml.space.patch.RPGItemPatch;
import com.mcml.space.patch.RecipeDupePatch;
import com.mcml.space.patch.SkullCrashPatch;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.AzureAPI.Coord;
import com.mcml.space.util.AzurePlayerList;
import com.mcml.space.util.Configurable;
import com.mcml.space.util.NetWorker;
import com.mcml.space.util.Perms;
import com.mcml.space.util.TPSAndThread;
import com.mcml.space.util.Utils;
import com.mcml.space.util.VersionLevel;
import com.mcml.space.util.VersionLevel.Version;

import lombok.val;

public class EscapeLag extends JavaPlugin implements Listener {
	public static EscapeLag MainThis;
	public static Coord<File, FileConfiguration> configOptimize;
	public static Coord<File, FileConfiguration> configPatch;
	public static Coord<File, FileConfiguration> configMain;
	public static Coord<File, FileConfiguration> configFunction;

	@Override
	public void onEnable() {
		MainThis = this;
		AzureAPI.bind(this);
		trySetupConfig();

		AzureAPI.log("EscapeLag —— 新一代的优化/稳定插件");
		AzureAPI.log("~(@^_^@)~ 玩的开心！~");

		AzureAPI.log("开始收集服务器信息中...");
		AzureAPI.log("Version " + getDescription().getVersion() + " is ready for installation \n");
		AzureAPI.log("Server: " + Bukkit.getServer().getVersion());
		AzureAPI.log("Bukkit: " + Bukkit.getServer().getBukkitVersion());
		AzureAPI.log("Level: " + VersionLevel.get() + "\n");

		AzureAPI.log("开始加载插件模块中...");

		if (ConfigOptimize.AutoSetenable == true) {
			try {
				EscapeLag.AutoSetServer();
			} catch (IOException | InterruptedException e) {
			}
		}

		AzurePlayerList.bind(this);
		AzurePlayerList.bind(new UpgradeNotifier());

		Perms.bind("EscapeLag.Admin");

		Bukkit.getPluginManager().registerEvents(new AntiInfItem(), this);
		Bukkit.getPluginManager().registerEvents(new AntiPortalInfItem(), this);
		Bukkit.getPluginManager().registerEvents(new AntiNetherHopperInfItem(), this);
		RPGItemPatch.init(this);
		Bukkit.getPluginManager().registerEvents(new ChunkKeeper(), this);
		Bukkit.getPluginManager().registerEvents(new NoCrowdEntity(), this);
		Bukkit.getPluginManager().registerEvents(new AntiCrashSign(), this);
		Bukkit.getPluginManager().registerEvents(new AntiSpam(), this);
		ExplosionController.init(this);
		Bukkit.getPluginManager().registerEvents(new AntiRedstone(), this);
		Bukkit.getPluginManager().registerEvents(new ItemClear(), this);
		Bukkit.getPluginManager().registerEvents(new NoSpawnChunks(), this);
		Bukkit.getPluginManager().registerEvents(new AntiInfRail(), this);
		Bukkit.getPluginManager().registerEvents(new AutoSave(), this);
		DupeLoginPatch.init(this);
		SpawnerController.init(this);
		AntiDupeDropItem.init();
		Bukkit.getPluginManager().registerEvents(new AntiInfSuagr(), this);
		Bukkit.getPluginManager().registerEvents(new AntiBedExplode(), this);
		Bukkit.getPluginManager().registerEvents(new WaterFlowLimitor(), this);
		Bukkit.getPluginManager().registerEvents(new FireLimitor(), this);
		Bukkit.getPluginManager().registerEvents(new FarmProtecter(), this);
		Bukkit.getPluginManager().registerEvents(new BonemealDupePatch(), this);
		Bukkit.getPluginManager().registerEvents(new AntiLongStringCrash(), this);
		Bukkit.getPluginManager().registerEvents(new TeleportPreLoader(), this);
		Bukkit.getPluginManager().registerEvents(new MonitorEnabler(), this);
		RespawnAction.init(this);
		EmptyRestart.init(this);
		CheatBookBlocker.init(this);
		OverloadRestart.init(this);
		SkullCrashPatch.init(this);

		if (VersionLevel.isHigherEquals(Version.MINECRAFT_1_12_R1)) {
			if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
				Bukkit.getPluginManager().registerEvents(new RecipeDupePatch(), this);
				AzureAPI.log("自动合成修复模块已启用");
			} else {
				AzureAPI.warn("检测到您正使用 1.12 版本的服务端, 但未安装 ProtocolLib 前置插件");
				AzureAPI.warn("这将导致某些重要的防护功能不可用, 强烈建议您安装 ProtocolLib 并重启服务端");
			}
		}

		ChunkKeeper.ChunkKeeperofTask();
		getServer().getScheduler().runTaskTimer(this, new ChunkUnloader(), 0,
				ConfigOptimize.ChunkUnloaderInterval * 20);

		TimerGarbageCollect.init(this);
		if (ConfigMain.AutoUpdate)
			Bukkit.getScheduler().runTaskAsynchronously(this, new NetWorker());
		Bukkit.getScheduler().runTaskTimer(this, new AntiFakeDeath(), 7 * 20, 7 * 20);
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new TPSAndThread(), 1, 1);

		AzureAPI.log("------加载完毕------");
		AzureAPI.log("乐乐感谢您的使用——有建议务必反馈，QQ1207223090");
		AzureAPI.log("您可以在插件根目录找到本插件的说明文档 说明文档.txt");
		List<String> devs = getDescription().getAuthors();
		AzureAPI.log("|||" + devs.get(0) + "/EscapeLag 合作作品.|||");
		AzureAPI.log("|||" + AzureAPI.contactBetween(devs, 1, ", ") + " 合作开发.|||");
		AzureAPI.log("§a您正在使用EscapeLag构建号 %BUILD_NUMBER%");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("el")) {
			sender.sendMessage("§b------§a§lEscapeLag - §e版本 " + getDescription().getVersion() + "§b------");
			if (Perms.has(sender)) {
				if (args.length == 0) {
					sender.sendMessage("§c请输入/el help 来获取帮助");
					return true;
				}
				if (args[0].equalsIgnoreCase("updateon")) {
					FileConfiguration MainConfig = configMain.getValue();
					MainConfig.set("AutoUpdate", true);
					try {
						MainConfig.save(configMain.getKey());
					} catch (IOException ex) {
					}
					ConfigMain.AutoUpdate = true;
					sender.sendMessage("§a§l[EscapeLag]§b已经成功开启自动更新！");
				}
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage("§e/el reload 重载插件");
					sender.sendMessage("§e/el chunkkeeper 查看关于区块保持者的帮助");
					sender.sendMessage("§e/el heap 查阅关于内存清理和分配的内容");
					sender.sendMessage("§e/el autosave 查阅关于自动储存的内容");
					sender.sendMessage("§e/el tps 查阅关于TPS和主线程");
					sender.sendMessage("§e/el autoset 查阅关于自动配端");
					sender.sendMessage("§e/el antiattack 查阅关于反压测模块");
					sender.sendMessage("§e/el monitor 查阅关于插件耗能侦测");
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
				if (args[0].equalsIgnoreCase("monitor")) {
					if (args.length == 1) {
						sender.sendMessage("§a后置参数:");
						sender.sendMessage("§eenable 打开插件耗时侦测");
						sender.sendMessage("§edisable 关闭插件耗时侦测");
						sender.sendMessage("§etopall <显示条数> 列表插件主线程总耗时");
						sender.sendMessage("§eeventall <显示条数> 查阅所有插件监听器耗时列表");
						sender.sendMessage("§ecommandall <显示条数> 查阅所有插件命令耗时列表");
						sender.sendMessage("§etaskall <显示条数> 查阅所有插件任务耗时列表");
						sender.sendMessage("§eevent <插件名字> 查阅插件监听器耗时列表");
						sender.sendMessage("§ecmd <插件名字> 查阅插件命令耗时列表");
						sender.sendMessage("§etask <插件名字> 查阅插件任务耗时列表");
						return true;
					}
					if (args[1].equalsIgnoreCase("enable")) {
						MonitorUtils.enable();
						sender.sendMessage("§e成功注入了插件侦测器！");
					}
					if (args[1].equalsIgnoreCase("disable")) {
						MonitorUtils.disable();
						sender.sendMessage("§e成功注销了插件侦测器！");
					}
					if (args[1].equalsIgnoreCase("event")) {
						Plugin plugin = Bukkit.getPluginManager().getPlugin(args[2]);
						if (plugin == null) {
							sender.sendMessage("§c错误！无法检查插件需要的数据！插件不存在？");
							return true;
						}
						sender.sendMessage("§e以下是指定插件监听器耗时排序列表：");
						sender.sendMessage("§a任务名字,§c耗时总量");
						Map<String, MonitorRecord> plugineventtime = MonitorUtils.getEventTimingsByPlugin(plugin);
						Iterator<MonitorRecord> itpet = plugineventtime.values().iterator();
						while (itpet.hasNext()) {
							MonitorRecord thispet = (MonitorRecord) itpet.next();
							sender.sendMessage(
									"§b|--§a" + thispet.getName() + " , §c" + thispet.getTotalTime() / 1000000000 + "秒");
						}
					}
					if (args[1].equalsIgnoreCase("command")) {
						Plugin plugin = Bukkit.getPluginManager().getPlugin(args[2]);
						if (plugin == null) {
							sender.sendMessage("§c错误！无法检查插件需要的数据！插件不存在？");
							return true;
						}
						sender.sendMessage("§e以下是指定插件命令耗时排序列表：");
						sender.sendMessage("§a指令,§c耗时总量");
						Map<String, MonitorRecord> plugincommandtime = MonitorUtils.getCommandTimingsByPlugin(plugin);
						Iterator<MonitorRecord> itpct = plugincommandtime.values().iterator();
						while (itpct.hasNext()) {
							MonitorRecord thispet = (MonitorRecord) itpct.next();
							sender.sendMessage(
									"§b|--§a" + thispet.getName() + " , §c" + thispet.getTotalTime() / 1000000000 + "秒");
						}
					}
					if (args[1].equalsIgnoreCase("task")) {
						Plugin plugin = Bukkit.getPluginManager().getPlugin(args[2]);
						if (plugin == null) {
							sender.sendMessage("§c错误！无法检查插件需要的数据！插件不存在？");
							return true;
						}
						sender.sendMessage("§e以下是指定插件任务耗时排序列表：");
						sender.sendMessage("§a监听器名字,§c耗时总量");
						MonitorRecord plugintasktime = MonitorUtils.getTaskTimingsByPlugin(plugin);
						sender.sendMessage(
								"§b|--§a" + plugintasktime.getName() + " , §c" + plugintasktime.getTotalTime() / 1000000000 + "秒");
					}
					if (args[1].equalsIgnoreCase("topall")) {
						if (args.length == 2) {
							sender.sendMessage("§e以下是插件耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								Map<String, MonitorRecord> plugineventtime = MonitorUtils
										.getEventTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpet = plugineventtime.values().iterator();
								while (itpet.hasNext()) {
									MonitorRecord thispet = (MonitorRecord) itpet.next();
									UsedTime = UsedTime + thispet.getTotalTime();
								}
								Map<String, MonitorRecord> plugincommandtime = MonitorUtils
										.getCommandTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpct = plugincommandtime.values().iterator();
								while (itpct.hasNext()) {
									MonitorRecord thispct = (MonitorRecord) itpct.next();
									UsedTime = UsedTime + thispct.getTotalTime();
								}
								MonitorRecord plugintasktime = MonitorUtils.getTaskTimingsByPlugin(plugin);
								UsedTime = UsedTime + plugintasktime.getTotalTime();
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							if (timetop.size() < 10) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < 10; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						} else {
							sender.sendMessage("§e以下是插件耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								Map<String, MonitorRecord> plugineventtime = MonitorUtils
										.getEventTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpet = plugineventtime.values().iterator();
								while (itpet.hasNext()) {
									MonitorRecord thispet = (MonitorRecord) itpet.next();
									UsedTime = UsedTime + thispet.getTotalTime();
								}
								Map<String, MonitorRecord> plugincommandtime = MonitorUtils
										.getCommandTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpct = plugincommandtime.values().iterator();
								while (itpct.hasNext()) {
									MonitorRecord thispct = (MonitorRecord) itpct.next();
									UsedTime = UsedTime + thispct.getTotalTime();
								}
								MonitorRecord plugintasktime = MonitorUtils.getTaskTimingsByPlugin(plugin);
								UsedTime = UsedTime + plugintasktime.getTotalTime();
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							int toShowLength = Integer.parseInt(args[2]);
							if (timetop.size() < toShowLength) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < toShowLength; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						}
					}
					if (args[1].equalsIgnoreCase("commandall")) {
						if (args.length == 2) {
							sender.sendMessage("§e以下是插件命令耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								Map<String, MonitorRecord> plugincommandtime = MonitorUtils
										.getCommandTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpct = plugincommandtime.values().iterator();
								while (itpct.hasNext()) {
									MonitorRecord thispct = (MonitorRecord) itpct.next();
									UsedTime = UsedTime + thispct.getTotalTime();
								}
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							if (timetop.size() < 10) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < 10; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						} else {
							sender.sendMessage("§e以下是插件命令耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								Map<String, MonitorRecord> plugincommandtime = MonitorUtils
										.getCommandTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpct = plugincommandtime.values().iterator();
								while (itpct.hasNext()) {
									MonitorRecord thispct = (MonitorRecord) itpct.next();
									UsedTime = UsedTime + thispct.getTotalTime();
								}
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							int toShowLength = Integer.parseInt(args[2]);
							if (timetop.size() < toShowLength) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < toShowLength; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						}
					}
					if (args[1].equalsIgnoreCase("taskall")) {
						if (args.length == 2) {
							sender.sendMessage("§e以下是插件任务耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								MonitorRecord plugintasktime = MonitorUtils.getTaskTimingsByPlugin(plugin);
								UsedTime = UsedTime + plugintasktime.getTotalTime();
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							if (timetop.size() < 10) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < 10; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						} else {
							sender.sendMessage("§e以下是插件任务耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								MonitorRecord plugintasktime = MonitorUtils.getTaskTimingsByPlugin(plugin);
								UsedTime = UsedTime + plugintasktime.getTotalTime();
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							int toShowLength = Integer.parseInt(args[2]);
							if (timetop.size() < toShowLength) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < toShowLength; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						}
					}
					if (args[1].equalsIgnoreCase("eventall")) {
						if (args.length == 2) {
							sender.sendMessage("§e以下是插件监听器耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								Map<String, MonitorRecord> plugineventtime = MonitorUtils
										.getEventTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpet = plugineventtime.values().iterator();
								while (itpet.hasNext()) {
									MonitorRecord thispet = (MonitorRecord) itpet.next();
									UsedTime = UsedTime + thispet.getTotalTime();
								}
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							if (timetop.size() < 10) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < 10; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						} else {
							sender.sendMessage("§e以下是插件监听器耗时排序列表：");
							sender.sendMessage("§a插件名字,§c耗时总量");
							Map<Plugin, Long> TimeMap = new HashMap<Plugin, Long>();
							Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
							int pl = plugins.length;
							for (int i = 0; i < pl; i++) {
								Plugin plugin = plugins[i];
								long UsedTime = 0L;
								Map<String, MonitorRecord> plugineventtime = MonitorUtils
										.getEventTimingsByPlugin(plugin);
								Iterator<MonitorRecord> itpet = plugineventtime.values().iterator();
								while (itpet.hasNext()) {
									MonitorRecord thispet = (MonitorRecord) itpet.next();
									UsedTime = UsedTime + thispet.getTotalTime();
								}
								UsedTime = UsedTime/1000000;
								TimeMap.put(plugin, UsedTime);
							}
							ArrayList<Map.Entry<Plugin, Long>> timetop = Utils.sortMap(TimeMap);
							int toShowLength = Integer.parseInt(args[2]);
							if (timetop.size() < toShowLength) {
								int tts = timetop.size();
								for (int i = 0; i < tts; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							} else {
								for (int i = 0; i < toShowLength; i++) {
									sender.sendMessage("§b|--§a" + timetop.get(i).getKey().getName() + " , §c"
											+ timetop.get(i).getValue() / 1000 + "秒");
								}
							}
						}
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
							EscapeLag.AutoSetServer();
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
						sender.sendMessage("§e目前以及被保存的区块列表:" + ChunkKeeper.ShouldKeepList);
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
						sender.sendMessage("§eclearchunk 执行一次检测清理区块");
						sender.sendMessage("§eheapshut 执行一次濒临崩溃内存检测");
						sender.sendMessage("§echunkunloadlog 查阅区块卸载计数器");
						sender.sendMessage("§edump 将服务器当前内存堆化为.hprof文件");
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
						getServer().getScheduler().runTask(this, new OverloadRestart());
						sender.sendMessage("§6成功检测一次内存濒临重启！");
					}
					if (args[1].equalsIgnoreCase("chunkunloadlog")) {
						sender.sendMessage("§a截止到目前，插件已经卸载了" + ChunkUnloader.ChunkUnloaderTimes + "个无用区块");
					}
					if (args[1].equalsIgnoreCase("dump")) {
						sender.sendMessage("§a开始 dump 内存堆！这可能会花费一些时间！");
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
				if (args[0].equalsIgnoreCase("tps")) {
					if (args.length == 1) {
						sender.sendMessage("§a后置参数:");
						sender.sendMessage("§esleep <ms> 停顿主线程毫秒");
						sender.sendMessage("§etps 获取服务器当前TPS");
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
					if (args[1].equalsIgnoreCase("tps")) {
						sender.sendMessage("§e目前服务器的TPS是 " + TPSAndThread.getTPS());
					}
				}
				if (args[0].equalsIgnoreCase("reload")) {
					trySetupConfig();
					sender.sendMessage("§a§l[EscapeLag]配置已经成功重载！");
					return true;
				}
			} else {
				sender.sendMessage("§a§l[EscapeLag]§4抱歉！您没有足够的权限！");
			}
			return true;
		}
		return false;
	}

	private void trySetupConfig() {
		try {
			setupConfig();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			AzureAPI.fatal("初始化配置文件时出错", this);
			e.printStackTrace();
		}
	}

	private void setupConfig() throws IllegalArgumentException, IllegalAccessException {
		this.saveResource("说明文档.txt", true);

		val pluginMainConfigFile = new File(this.getDataFolder(), "PluginMainConfig.yml");
		configMain = AzureAPI.wrapCoord(pluginMainConfigFile, AzureAPI.loadOrCreateFile(pluginMainConfigFile));

		val clearLagConfig = new File(this.getDataFolder(), "ClearLagConfig.yml");
		configOptimize = AzureAPI.wrapCoord(clearLagConfig, AzureAPI.loadOrCreateFile(clearLagConfig));

		val antiBugConfig = new File(this.getDataFolder(), "AntiBugConfig.yml");
		configPatch = AzureAPI.wrapCoord(antiBugConfig, AzureAPI.loadOrCreateFile(antiBugConfig));

		val doEventConfig = new File(this.getDataFolder(), "DoEventConfig.yml");
		configFunction = AzureAPI.wrapCoord(doEventConfig, AzureAPI.loadOrCreateFile(doEventConfig));

		try {
			Configurable.restoreNodes(configMain, ConfigMain.class);
		} catch (IOException io) {
			notifyFileException(pluginMainConfigFile);
		}

		try {
			Configurable.restoreNodes(configOptimize, ConfigOptimize.class);
		} catch (IOException io) {
			notifyFileException(clearLagConfig);
		}

		try {
			Configurable.restoreNodes(configPatch, ConfigPatch.class);
		} catch (IOException io) {
			notifyFileException(antiBugConfig);
		}

		try {
			Configurable.restoreNodes(configFunction, ConfigFunction.class);
		} catch (IOException io) {
			notifyFileException(doEventConfig);
		}

		AzureAPI.setPrefix(
				ChatColor.translateAlternateColorCodes('&', ConfigMain.PluginPrefix) + ChatColor.RESET + " > ");
	}

	private static void notifyFileException(File file) {
		AzureAPI.warn("配置文件" + file.getName() + "无法正常读取或存储, 请检查文件情况");
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
			if (heapmb <= 4000) {
				bukkit.set("ticks-per.monster-spawns", 2);
			}
			bukkit.set("EscapeLag.Changed", "如果Config的AutoSet开启，该参数会被改变。");
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
			spigot.set("EscapeLag.Changed", "如果Config的AutoSet开启，该参数会被改变。");
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
			if (bukkit.getInt("EscapeLag.SetStep") == 1) {
				bukkit.set("EscapeLag.SetStep", 2);
				try {
					bukkit.save(BukkitFile);
				} catch (IOException ex) {
				}
			}
			if (bukkit.getInt("VLagger.SetStep") == 0) {
				bukkit.set("EscapeLag.SetStep", 1);
				bukkit.save(BukkitFile);
				AzureAPI.log("成功改动服务器配端，正在重启来启用它.");
				AzureAPI.RestartServer();
			}
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("EscapeLag —— 已经停止使用");
		getLogger().info("感谢您的使用——乐乐");
	}

	public static File getPluginsFile() {
		return EscapeLag.MainThis.getFile();
	}
}
