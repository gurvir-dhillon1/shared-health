package shared.health.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import shared.health.manager.DeadManager;
import shared.health.manager.SharedHealthManager;

public class SharedHealthListener implements Listener {
  private final SharedHealthManager healthManager;
  private final DeadManager deadManager;

  public SharedHealthListener(SharedHealthManager manager, DeadManager deadManager) {
    this.healthManager = manager;
    this.deadManager = deadManager;
  }

  @EventHandler
  public void onPlayerDamage(EntityDamageEvent event) {
    if (event.getEntity() instanceof Player p) {
      if (this.deadManager.isPlayerDead(p)) return;
      event.setCancelled(true);
      healthManager.subtractSharedHealth(event.getFinalDamage());
    }
  }

  @EventHandler
  public void onPlayerHeal(EntityRegainHealthEvent event) {
    if (event.getEntity() instanceof Player p) {
      if (this.deadManager.isPlayerDead(p)) return;
      event.setCancelled(true);
      healthManager.addSharedHealth(event.getAmount());
    }
  }

}
