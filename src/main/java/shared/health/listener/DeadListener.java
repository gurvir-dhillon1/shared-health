package shared.health.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class DeadListener implements Listener {


  @EventHandler
  public void onPlayerDeath(EntityDeathEvent event) {
  }

}