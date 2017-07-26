package com.mcml.space.util;

import static com.mcml.space.util.VersionLevel.isPaper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author SotrForgotten
 */
public class AzureAPI {
    private static String loggerPrefix = "";
    private static final int bukkitVDChunk = (Bukkit.getViewDistance() * 2) ^ 2 + 1;
    private static final int bukkitVDBlock = Bukkit.getViewDistance() * 16;

    private static final class LazyAPI {
        private static final AzureAPI api = new AzureAPI();
    }

    private AzureAPI() {
        assert Bukkit.getServer() != null;
        assert LazyAPI.api == null;
    }

    public static final AzureAPI getAPI() {
        return LazyAPI.api;
    }

    public static int viewDistance(final Player player) {
        return isPaper() ? player.getViewDistance() : Bukkit.getViewDistance();
    }

    public static int viewDistanceBlock(final Player player) {
        if (customViewDistance(player)) return player.getViewDistance() * 16;
        return bukkitVDBlock;
    }

    public static int viewDistanceChunk(final Player player) {
        if (customViewDistance(player)) return (player.getViewDistance() * 2) ^ 2 + 1;
        return bukkitVDChunk;
    }

    public static boolean customViewDistance(final Player player) {
        if (!isPaper()) return false;
        return Bukkit.getViewDistance() != player.getViewDistance();
    }

    public static String setPrefix(final String prefix) {
        loggerPrefix = prefix;
        return prefix;
    }

    public static void resetPrefix() {
        loggerPrefix = "";
    }
    
    public static void fatal(final String context) {
        fatal(loggerPrefix, context);
    }
    
    public static void fatal(final String prefix, final String context) {
        Bukkit.getLogger().severe(prefix + context);
        Bukkit.shutdown();
    }
    
    public static void warn(final String context) {
        warn(loggerPrefix, context);
    }
    
    public static void warn(final String prefix, final String context) {
        if (StringUtils.isBlank(context)) return;
        Bukkit.getLogger().warning(prefix + context);
    }
    
    public static void log(final String context) {
        log(loggerPrefix, context);
    }

    public static void log(final String prefix, final String context) {
        if (StringUtils.isBlank(context)) return;
        Bukkit.getConsoleSender().sendMessage(prefix + context);
    }

    public static void log(final CommandSender sender, final String context) {
        log(sender, loggerPrefix, context);
    }

    public static void log(final CommandSender sender, final String prefix, final String msg) {
        if (StringUtils.isBlank(msg)) return;
        sender.sendMessage(prefix + msg);
    }
    
    public static void bc(final String context) {
        bc(loggerPrefix, context);
    }
    
    public static void bc(final String prefix, final String context) {
        if (StringUtils.isBlank(context)) return;
        Bukkit.broadcastMessage(prefix + context);
    }

    public static long toTicks(TimeUnit unit, long duration) {
        return unit.toSeconds(duration) * 20;
    }

    public static Logger createLogger(final String prefix) {
        assert prefix != null;
        return new PrefixedLogger(prefix);
    }

    protected static class PrefixedLogger extends Logger {
        protected final String prefix;

        protected PrefixedLogger(final String prefix) {
            super(prefix, null);
            this.prefix = prefix;
            setParent(Logger.getGlobal());
            setLevel(Level.INFO);
        }

        @Override
        public void log(final LogRecord logRecord) {
            if (this.isLoggable(logRecord.getLevel())) Bukkit.getConsoleSender().sendMessage(prefix + logRecord.getMessage());
        }
    }

    public static Coord2D wrapCoord2D(int x, int z) {
        return new Coord2D(x, z);
    }

    public static class Coord2D {
        final int x;
        final int z;

        public Coord2D(int x2d, int z2d) {
            x = x2d;
            z = z2d;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
    }

    public static <E> Map<String, E> newCaseInsensitiveMap() {
        return new CaseInsensitiveMap<E>();
    }

    public static Set<String> newCaseInsensitiveSet() {
        return Sets.newSetFromMap(new CaseInsensitiveMap<Boolean>());
    }
    
    public static <E> List<E> matchElements(List<E> list, int start) {
        return matchElements(list, start, list.size() - 1);
    }
    
    /**
     * Returns elements between the start and end index, included the edge as well, collect to a list with capacity 'end - start + 1'
     */
    public static <E> List<E> matchElements(List<E> list, int start, int end) {
        List<E> t = Lists.newArrayListWithCapacity(end - start + 1);
        for (; start <= end; start++) {
            t.add(list.get(start));
        }
        return t;
    }
    
    public static String contactBetween(List<String> list, int start, char spilt) {
        return contactBetween(list, start, spilt);
    }
    
    public static String contactBetween(List<String> list, int start, String spilt) {
        return contactBetween(list, start, list.size() - 1, spilt);
    }
    
    public static String contactBetween(List<String> list, int start, int end, char spilt) {
        return contactBetween(list, start, end, spilt);
    }
    
    /**
     * Contacts strings between the start and end index, included the edge as well, then spilt with the given string
     */
    public static String contactBetween(List<String> list, int start, int end, String spilt) {
        String r = "";
        for (; start <= end; start++) {
            r = r.concat(list.get(start) + (start == end ? "" : spilt));
        }
        return r;
    }
    
    public static ChainArrayList<String> newChainStringList() { // TODO: any
        return new ChainArrayList<>();
    }
    
    @SuppressWarnings("serial")
    public static class ChainArrayList<E> extends ArrayList<E> {
        public ChainArrayList<E> to(E e) {
            super.add(e);
            return this;
        }
    }
    
    public static boolean hasPerm(CommandSender sender, String perm) {
        return sender.isOp() || sender.hasPermission(perm);
    }
    
    public static boolean hasPerm(CommandSender sender, Permission perm) {
        return sender.isOp() || sender.hasPermission(perm);
    }

}
