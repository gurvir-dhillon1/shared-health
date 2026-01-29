package shared.health.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

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
    // TODO: Stop infinite damage loop through some kind of flag or damage tracking
    healthManager.damageOtherPlayers(event.getFinalDamage(), event.getEntity(), event.getDamageSource());
  }

  @EventHandler
  public void onPlayerHeal(EntityRegainHealthEvent event) {
    if (event.getEntity() instanceof Player) {
      event.setCancelled(true);
      healthManager.healOtherPlayers(event.getAmount());
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    this.healthManager.handleServerDeath(event.getEntity(), event.getDamageSource());
  }

}
