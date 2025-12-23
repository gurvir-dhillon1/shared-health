package shared.health.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import shared.health.controller.RespawnController;
import shared.health.manager.DeadManager;
import shared.health.manager.RespawnVoteManager;

public class RespawnVoteListener implements Listener{

  private final DeadManager deadManager;
  private final RespawnVoteManager respawnVoteManager;
  private final RespawnController respawnController;

  public RespawnVoteListener(DeadManager deadManager, RespawnController respawnController, RespawnVoteManager respawnVoteManager) {
    this.deadManager = deadManager;
    this.respawnController = respawnController;
    this.respawnVoteManager = respawnVoteManager;
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    respawnVoteManager.vote(event.getPlayer());
    if (respawnVoteManager.hasMajority(Bukkit.getOnlinePlayers().size()))
      respawnController.respawnAllPlayers();
  }
}
