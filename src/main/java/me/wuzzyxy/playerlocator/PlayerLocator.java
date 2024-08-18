package me.wuzzyxy.playerlocator;

import me.wuzzyxy.playerlocator.configs.ConfigManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerLocator extends JavaPlugin {

    private ConfigManager config = null;
    private CheckNearbyPlayersTask checkNearbyPlayersTask = null;
    @Override
    public void onEnable() {
        config = new ConfigManager(this);
        checkNearbyPlayersTask = new CheckNearbyPlayersTask(this);

        new MainCommand(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigManager getPluginConfig() {
        return config;
    }

    public void reloadConfig() {
        config.reloadConfig();
        checkNearbyPlayersTask.cancel();
        checkNearbyPlayersTask = new CheckNearbyPlayersTask(this);
    }

}
