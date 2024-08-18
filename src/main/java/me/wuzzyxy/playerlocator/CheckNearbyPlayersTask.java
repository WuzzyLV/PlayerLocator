package me.wuzzyxy.playerlocator;

import me.wuzzyxy.playerlocator.configs.ConfigManager;
import me.wuzzyxy.playerlocator.configs.ConfigPaths;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class CheckNearbyPlayersTask {
    private final PlayerLocator plugin;
    private final FileConfiguration config;
    private final BukkitTask task;
    public CheckNearbyPlayersTask(PlayerLocator plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig().getConfig();

        closestDistanceSquaredMax = config.getInt(ConfigPaths.MINIMUM_DISTANCE.getPath()) * config.getInt(ConfigPaths.MINIMUM_DISTANCE.getPath());
        activeWorlds = config.getStringList(ConfigPaths.WORLDS.getPath());
        playerNearbyImageTemplate = config.getString(ConfigPaths.PLAYER_NEARBY_MESSAGE.getPath());
        noPlayersImageTemplate = config.getString(ConfigPaths.NO_PLAYERS_MESSAGE.getPath());
        task = new BukkitRunnable() {
            @Override
            public void run() {
                checkNearbyPlayers();
            }
        }.runTaskTimer(plugin, 0L, config.getInt(ConfigPaths.INTERVAL.getPath()));
    }

    double closestDistanceSquaredMax;
    List<String> activeWorlds;
    String playerNearbyImageTemplate;
    String noPlayersImageTemplate;

    private void checkNearbyPlayers() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (activeWorlds.contains(player.getWorld().getName())) {
                Player closestPlayer = null;
                double closestDistanceSquared = Double.MAX_VALUE;

                for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
                    if (otherPlayer.equals(player) || !otherPlayer.getWorld().equals(player.getWorld())) {
                        continue; // Skip if it's the same player or in a different world
                    }

                    double distanceSquared = player.getLocation().distanceSquared(otherPlayer.getLocation());
                    if (distanceSquared < closestDistanceSquared) {
                        closestDistanceSquared = distanceSquared;
                        closestPlayer = otherPlayer;
                    }
                }
                if (closestDistanceSquared > closestDistanceSquaredMax) {
                    player.sendActionBar(MiniMessage.miniMessage().deserialize(noPlayersImageTemplate));
                    continue;
                }

                if (closestPlayer != null) {
                    double distance = Math.floor(Math.sqrt(closestDistanceSquared));
                    String actionBarMessage = playerNearbyImageTemplate
                            .replace("%player%", closestPlayer.getName())
                            .replace("%distance%", String.format("%.0f", distance));
                    player.sendActionBar(MiniMessage.miniMessage().deserialize(actionBarMessage));
                }
            }
        }
    }

    public void cancel() {
        task.cancel();
    }
}
