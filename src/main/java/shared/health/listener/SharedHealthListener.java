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
    healthManager.setOtherPlayersHealth(-1 * event.getFinalDamage(), event.getEntity(), event.getDamageSource());
  }

  @EventHandler
  public void onPlayerHeal(EntityRegainHealthEvent event) {
    if (event.getEntity() instanceof Player) {
      healthManager.healOtherPlayers(event.getAmount(), event.getEntity());
    }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (this.healthManager.isSlaughtering()) return;
    this.healthManager.handleServerDeath(event.getEntity(), event.getDamageSource());
  }

}
