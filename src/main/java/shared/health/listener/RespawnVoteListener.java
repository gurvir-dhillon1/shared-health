package shared.health.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import shared.health.controller.RespawnController;
import shared.health.manager.RespawnVoteManager;

public class RespawnVoteListener implements Listener{

  private final RespawnVoteManager respawnVoteManager;
  private final RespawnController respawnController;

  public RespawnVoteListener(RespawnController respawnController, RespawnVoteManager respawnVoteManager) {
    this.respawnController = respawnController;
    this.respawnVoteManager = respawnVoteManager;
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    this.respawnVoteManager.vote(event.getPlayer());
    if (this.respawnVoteManager.hasMajority(Bukkit.getOnlinePlayers().size())) {
      this.respawnController.respawnAllPlayers();
      this.respawnVoteManager.reset();
    }
  }
}
