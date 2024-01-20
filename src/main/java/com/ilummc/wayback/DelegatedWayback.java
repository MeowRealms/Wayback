package com.ilummc.wayback;

import com.ilummc.wayback.backups.FileBackup;
import com.ilummc.wayback.cmd.CommandRegistry;
import com.ilummc.wayback.compress.ZipCompressor;
import com.ilummc.wayback.policy.*;
import com.ilummc.wayback.schedules.PreloadSchedule;
import com.ilummc.wayback.storage.FtpStorage;
import com.ilummc.wayback.storage.LocalStorage;
import com.ilummc.wayback.tasks.RollbackTask;
import com.ilummc.wayback.tasks.TransferTask;
import com.ilummc.wayback.util.Language;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;

import static com.ilummc.wayback.Wayback.instance;

final class DelegatedWayback {

    static void onEnable() {
        Language.sendToConsole("LOGO", instance().getDescription().getVersion());
        registerSerializable();
        WaybackConf.init();
        CommandRegistry.init();
        CommandRegistry.register(new WaybackCommand());
    }

    private static void registerSerializable() {
        ConfigurationSerialization.registerClass(FileBackup.class, "File");
        ConfigurationSerialization.registerClass(FileBackup.class, "FileBackup");
        ConfigurationSerialization.registerClass(ZipCompressor.class, "zip");
        ConfigurationSerialization.registerClass(FtpStorage.class, "FtpStorage");
        ConfigurationSerialization.registerClass(LocalStorage.class, "LocalStorage");
        ConfigurationSerialization.registerClass(AbandonPolicy.class, "Abandon");
        ConfigurationSerialization.registerClass(CleanOldestPolicy.class, "CleanOldest");
        ConfigurationSerialization.registerClass(CleanLatestPolicy.class, "CleanLatest");
        ConfigurationSerialization.registerClass(CleanExceedPolicy.class, "CleanExceed");
        ConfigurationSerialization.registerClass(RetryPolicy.class, "Retry");
        ConfigurationSerialization.registerClass(TransferTask.class, "Transfer");
        ConfigurationSerialization.registerClass(PreloadSchedule.NormalPreload.class, "Instant");
        ConfigurationSerialization.registerClass(PreloadSchedule.PeriodPreload.class, "Period");
        ConfigurationSerialization.registerClass(PreloadSchedule.DelayedPreload.class, "Delayed");
        ConfigurationSerialization.registerClass(RollbackTask.class, "Rollback");
    }

    static void onDisable() {
        try {
            HandlerList.unregisterAll(instance());
            Wayback.getSchedules().shutdown();
        } catch (InterruptedException e) {
            Wayback.logger().error("TERMINATE_ERROR");
        }
    }

}
