package shared.health.controller;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import shared.health.manager.DeadManager;
import shared.health.manager.SharedHealthManager;

public class RespawnController {
    private final DeadManager deadManager;
    private final SharedHealthManager sharedHealthManager;
    private final Plugin plugin;

    public RespawnController(DeadManager deadManager, SharedHealthManager sharedHealthManager, Plugin plugin) {
        this.deadManager = deadManager;
        this.sharedHealthManager = sharedHealthManager;
        this.plugin = plugin;
    }

    public void respawnAllPlayers() {
        for (UUID id : deadManager.getDeadPlayers()) {
            Player p = Bukkit.getPlayer(id);
            if (p != null)
                Bukkit.getScheduler().runTask(plugin, p.spigot()::respawn);
        }
        deadManager.clearAllDeadPlayers();
        sharedHealthManager.fillSharedHealth();
    }


}
