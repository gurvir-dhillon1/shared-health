package shared.health.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import shared.health.manager.DeadManager;

public class DeadListener implements Listener {
  private DeadManager deadManager;

  public DeadListener(DeadManager deadManager) {
    this.deadManager = deadManager;
  }

  @EventHandler
  public void onPlayerDeath(EntityDeathEvent event) {
    if (event.getEntity() instanceof Player p) {
      this.deadManager.addDeadPlayer(p);
    }
  }

}