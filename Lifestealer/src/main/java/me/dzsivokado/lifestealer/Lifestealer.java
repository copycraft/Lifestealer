package me.dzsivokado.lifestealer;

import me.dzsivokado.lifestealer.commands.giveKard;
import me.dzsivokado.lifestealer.listeners.lifestealListener;
import me.dzsivokado.lifestealer.listeners.banisherListener;
import me.dzsivokado.lifestealer.listeners.eggListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Lifestealer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Lifestealer plugin has been enabled!");

        // Register commands
        this.getCommand("givesword").setExecutor(new giveKard());

        // Register event listeners
        getServer().getPluginManager().registerEvents(new lifestealListener(this), this);
        getServer().getPluginManager().registerEvents(new banisherListener(), this);
        getServer().getPluginManager().registerEvents(new eggListener(), this);

        // Check for updates at startup
        checkForUpdates();

        // Schedule daily update checks
        scheduleDailyUpdateCheck();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Lifestealer plugin has been disabled!");
    }

    private void checkForUpdates() {
        Updater updater = new Updater(this);
        updater.checkForUpdates();
    }

    private void scheduleDailyUpdateCheck() {
        long ticksPerDay = 20L * 60L * 60L * 24L; // 20 ticks per second * 60 seconds per minute * 60 minutes per hour * 24 hours per day

        new BukkitRunnable() {
            @Override
            public void run() {
                checkForUpdates();
            }
        }.runTaskTimer(this, ticksPerDay, ticksPerDay); // Schedule to run daily
    }
}
