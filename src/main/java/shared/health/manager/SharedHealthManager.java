package shared.health.manager;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class SharedHealthManager {
  private boolean slaughter = false;
  private final Plugin plugin;

  public SharedHealthManager(Plugin plugin) {
    this.plugin = plugin;
  }

  public boolean isSlaughtering() {
    return this.slaughter;
  }

  private void setSlaughter(boolean slaughter) {
    this.slaughter = slaughter;
  }

  public void healOtherPlayers(double amount, Entity p) {
    this.setOtherPlayersHealth(amount, p, null);
  }

  public void setOtherPlayersHealth(double amount, Entity p, DamageSource e) {
    if (this.isSlaughtering()) return;
    for (var otherPlayer : Bukkit.getOnlinePlayers()) {
      if (otherPlayer == p) continue;
      otherPlayer.setHealth(Math.max(0,Math.min(20, ((Player) p).getHealth() + amount)));
      if (amount < 0) this.spawnParticles(otherPlayer);
    }
  }

  public void syncHealth(Player p) {
    // TODO: Implement health synchronization
  }

  public void handleServerDeath() {
    this.setSlaughter(true);
    this.plugin.getServer().getScheduler().runTask(plugin, () -> {
      for (var p : Bukkit.getOnlinePlayers())
        p.setHealth(0.0);
     });
    this.setSlaughter(false);
  }

  private void spawnParticles(Player p) {
    Location eyeLoc = p.getEyeLocation();
    Vector direction = eyeLoc.getDirection();
    double distanceInFront = 0.6;
    Location particleLoc = eyeLoc.clone().add(direction.multiply(distanceInFront));
    p.getWorld().spawnParticle(
      Particle.ENTITY_EFFECT,
      particleLoc,
      7,
      0.3, 0.3, 0.3,
      0,
      Color.fromRGB(255, 50, 50)
    );
  }
}
