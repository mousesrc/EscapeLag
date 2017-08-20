package com.mcml.space.monitor.inject;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import com.mcml.space.config.ConfigOptimize;
import com.mcml.space.util.AzureAPI;
import com.mcml.space.util.Reflection;
import com.mcml.space.util.Reflection.FieldAccessor;

public class CommandInjector extends AbstractMultipleInjector implements TabExecutor {
	/**
	 * 
	 * @author jiongjionger,Vlvxingze
	 */

	public static void inject(Plugin plg) {
		if (plg != null) {
			try {
				SimpleCommandMap simpleCommandMap = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
				for (Command command : simpleCommandMap.getCommands()) {
					if (command instanceof PluginCommand) {
						PluginCommand pluginCommand = (PluginCommand) command;
						if (plg.equals(pluginCommand.getPlugin())) {
							FieldAccessor<CommandExecutor> commandField = Reflection.getField(PluginCommand.class, "executor", CommandExecutor.class);
							FieldAccessor<TabCompleter> tabField = Reflection.getField(PluginCommand.class, "completer", TabCompleter.class);
							CommandInjector commandInjector = new CommandInjector(plg, commandField.get(pluginCommand), tabField.get(pluginCommand));
							commandField.set(pluginCommand, commandInjector);
							tabField.set(pluginCommand, commandInjector);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void uninject(Plugin plg) {
		if (plg != null) {
			try {
				SimpleCommandMap simpleCommandMap = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class).get(Bukkit.getPluginManager());
				for (Command command : simpleCommandMap.getCommands()) {
					if (command instanceof PluginCommand) {
						PluginCommand pluginCommand = (PluginCommand) command;
						if (plg.equals(pluginCommand.getPlugin())) {
							FieldAccessor<CommandExecutor> commandField = Reflection.getField(PluginCommand.class, "executor", CommandExecutor.class);
							FieldAccessor<TabCompleter> tabField = Reflection.getField(PluginCommand.class, "completer", TabCompleter.class);
							CommandExecutor executor = commandField.get(pluginCommand);
							if (executor instanceof CommandInjector) {
								commandField.set(pluginCommand, ((CommandInjector) executor).getCommandExecutor());
							}
							TabCompleter completer = tabField.get(pluginCommand);
							if (completer instanceof CommandInjector) {
								tabField.set(pluginCommand, ((CommandInjector) executor).getTabCompleter());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private final CommandExecutor commandExecutor;
	private final TabCompleter tabCompleter;

	public CommandInjector(Plugin plugin, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		super(plugin);
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}

	public CommandExecutor getCommandExecutor() {
		return this.commandExecutor;
	}

	public TabCompleter getTabCompleter() {
		return this.tabCompleter;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Bukkit.isPrimaryThread()) {
			long startTime = System.nanoTime();
			boolean commandResult;
			try {
				commandResult = this.commandExecutor.onCommand(sender, command, label, args);
			} finally {
				long endTime = System.nanoTime();
				long useTime = endTime - startTime;
				if(ConfigOptimize.MonitorPluginLagWarningenable){
					if(useTime/1000000 > ConfigOptimize.MonitorPluginLagWarningPeriod){
						AzureAPI.log("警告！服务器主线程陷入停顿超过配置设定值！因为插件" + this.getPlugin().getName() + " 执行了一次耗时 " + useTime/1000000 + " 毫秒的 " + command.getName() + " 指令操作！");
					}
				}
				this.record(command.getName(), useTime);
			}
			return commandResult;
		} else {
			return this.commandExecutor.onCommand(sender, command, label, args);
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (tabCompleter == null)
			return null; // onTabComplete 返回 null 表示使用默认 completer
		return this.tabCompleter.onTabComplete(sender, command, alias, args);
	}
}
