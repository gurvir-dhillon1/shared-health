package shared.health.controller;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import shared.health.manager.SharedHealthManager;

public class RespawnController {
    private final SharedHealthManager sharedHealthManager;
    private final Plugin plugin;

    public RespawnController(SharedHealthManager sharedHealthManager, Plugin plugin) {
        this.sharedHealthManager = sharedHealthManager;
        this.plugin = plugin;
    }

    public void respawnAllPlayers() {
        Bukkit.getScheduler().runTask(plugin, () -> {
            for (var p : Bukkit.getOnlinePlayers())
                Bukkit.getScheduler().runTask(plugin, p.spigot()::respawn);

            sharedHealthManager.fillSharedHealth();
        });
    }
}
