package com.ilummc.wayback;

import com.ilummc.wayback.cmd.WaybackTabCompleter;
import com.ilummc.wayback.schedules.WaybackSchedules;
import com.ilummc.wayback.util.Language;
import com.ilummc.wayback.util.logger.TLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wayback extends JavaPlugin {

    private TLogger logger;

    private boolean loaded = false;

    private boolean disabling = false;

    private static Wayback instance;

    public static WaybackSchedules getSchedules() {
        return WaybackSchedules.instance();
    }

    public static WaybackConf getConf() {
        return WaybackConf.getConf();
    }

    public static TLogger logger() {
        return instance().logger;
    }

    public static Wayback instance() {
        return instance;
    }

    public static boolean isDisabling() {
        return instance().disabling;
    }

    public Wayback() {

    }

    public static boolean reload() {
        try {
            instance().onDisable();
            WaybackConf.getConf().cleanSchedules();
            WaybackSchedules.renew();
            Wayback.instance().reloadConfig();
            Language.initialize();
            instance().onEnable();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onLoad() {
        instance = this;
        Language.initialize();
        logger = new TLogger("[{0}][{1}§f] {2}", instance(), TLogger.VERBOSE);
    }

    @Override
    public void onEnable() {
        if (!loaded)
            try {
                DelegatedWayback.onEnable();
                getCommand("wayback").setTabCompleter(new WaybackTabCompleter());
                loaded = true;
            } catch (Throwable t) {
                logger.fatal("ERR_LOAD_WAYBACK");
                t.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(this);
            }
        else loaded = false;
    }

    @Override
    public void onDisable() {
        while (disabling) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
        if (loaded) {
            loaded = false;
            disabling = true;
            DelegatedWayback.onDisable();
            disabling = false;
        }
    }

}
