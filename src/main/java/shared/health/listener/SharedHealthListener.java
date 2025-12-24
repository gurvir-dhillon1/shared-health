package shared.health.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import shared.health.manager.SharedHealthManager;

public class SharedHealthListener implements Listener {
  private final SharedHealthManager healthManager;

  public SharedHealthListener(SharedHealthManager manager) {
    this.healthManager = manager;
  }

  @EventHandler
  public void onPlayerDamage(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Player)) return;
    if (this.healthManager.isSlaughtering()) return;
    healthManager.subtractSharedHealth(event.getFinalDamage(), event.getEntity(), event.getDamageSource());
    event.setDamage(0.0);
  }

  @EventHandler
  public void onPlayerHeal(EntityRegainHealthEvent event) {
    if (event.getEntity() instanceof Player) {
      event.setCancelled(true);
      healthManager.addSharedHealth(event.getAmount());
    }
  }

}
