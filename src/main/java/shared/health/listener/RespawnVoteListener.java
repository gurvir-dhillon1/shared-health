package shared.health.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import shared.health.controller.RespawnVoteController;
import shared.health.manager.DeadManager;
import shared.health.manager.RespawnVoteManager;

public class RespawnVoteListener implements Listener{

  private final DeadManager deadManager;
  private final RespawnVoteManager respawnVoteManager;
  private final RespawnVoteController respawnVoteController;

  public RespawnVoteListener(DeadManager deadManager, RespawnVoteController respawnVoteController, RespawnVoteManager respawnVoteManager) {
    this.deadManager = deadManager;
    this.respawnVoteController = respawnVoteController;
    this.respawnVoteManager = respawnVoteManager;
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {

  }
}
