package com.ilummc.wayback.util;

import com.ilummc.wayback.Wayback;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Wayback
 * com.ilummc.wayback.util.Language
 *
 * @author mical
 * @since 2023/4/2 2:54 PM
 */
public class Language {

    private static FileConfiguration config;

    public static void initialize() {
        final File file = new File(Wayback.instance().getDataFolder(), "language.yml");
        if (!file.exists()) {
            Wayback.instance().saveResource("language.yml", true);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void sendTo(final CommandSender sender, final String node, final Object... args) {
        if (config.isList(node)) {
            config.getStringList(node).forEach(line -> sender.sendMessage(colored(MessageFormat.format(line, args))));
            return;
        }
        sender.sendMessage(colored(MessageFormat.format(Objects.requireNonNull(config.getString(node, "")), args)));
    }

    public static void sendToConsole(final String node, final Object... args) {
        sendTo(Bukkit.getConsoleSender(), node, args);
    }

    /**
     * 获取内容
     * @param node 节点
     * @param args 替换变量
     * @return 如果为 List, 则会用 \n 分隔开.
     */
    public static String asString(final String node, final Object... args) {
        if (config.isList(node)) {
            return config.getStringList(node).stream().map(Language::colored).map(s -> MessageFormat.format(s, args)).collect(Collectors.joining("\n"));
        }
        return colored(MessageFormat.format(Objects.requireNonNull(config.getString(node, "")), args));
    }

    public static String colored(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
