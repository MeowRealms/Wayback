package com.ilummc.wayback.util.logger;

import com.ilummc.wayback.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 日志工具
 *
 * @author sky
 */
public class TLogger {

    public static final int VERBOSE = 0, FINEST = 1, FINE = 2, INFO = 3, WARN = 4, ERROR = 5, FATAL = 6;

    private final String pattern;
    private final String name;
    private int level;

    public TLogger(String pattern, Plugin plugin, int level) {
        this.pattern = pattern;
        this.name = plugin.getName();
        this.level = level;
    }

    public TLogger(String pattern, String name, int level) {
        this.pattern = pattern;
        this.name = name;
        this.level = level;
    }

    public static TLogger getUnformatted(Plugin plugin) {
        return new TLogger("§8[§3§l{0}§8][§r{1}§8] §f{2}", plugin, TLogger.VERBOSE);
    }

    public static TLogger getUnformatted(String name) {
        return new TLogger("§8[§3§l{0}§8][§r{1}§8] §f{2}", name, TLogger.VERBOSE);
    }

    public String getPattern() {
        return pattern;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void verbose(String node, Object... args) {
        if (level <= VERBOSE) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§f全部", Language.colored(msg))));
        }
    }

    public void finest(String node, Object... args) {
        if (level <= FINEST) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§e良好", Language.colored(msg))));
        }
    }

    public void fine(String node, Object... args) {
        if (level <= FINE) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§a正常", Language.colored(msg))));
        }
    }

    public void info(String node, Object... args) {
        if (level <= INFO) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§b信息", Language.colored(msg))));
        }
    }

    public void warn(String node, Object... args) {
        if (level <= WARN) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§6警告", Language.colored(msg))));
        }
    }

    public void error(String node, Object... args) {
        if (level <= ERROR) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§c错误", Language.colored(msg))));
        }
    }

    public void fatal(String node, Object... args) {
        if (level <= FATAL) {
            Arrays.asList(Language.asString(node, args).split("\n")).forEach(msg -> Bukkit.getConsoleSender().sendMessage(MessageFormat.format(pattern, name, "§4致命错误", Language.colored(msg))));
        }
    }
}
