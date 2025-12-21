package shared.health.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import shared.health.DeadManager;

public class DeadListener {
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

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
      this.deadManager.removeDeadPlayer(event.getPlayer());
      /*
      TO DO: override default respawn button event to make it into a button that removes players
      from the deadPlayer set and once everyone is alive it can respawn everyone
      */
      if (this.deadManager.areAllAlive()) {}
  }
}