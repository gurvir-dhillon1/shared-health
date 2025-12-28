package shared.health.manager;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;

public class SharedHealthManager {
  private double sharedHealth;
  private final double maxHealth;
  private boolean slaughter = false;
  private final Plugin plugin;

  public SharedHealthManager(Plugin plugin, double startingHealth) {
    this.plugin = plugin;
    this.maxHealth = startingHealth;
    this.sharedHealth = startingHealth;
  }

  public double getSharedHealth() {
    return this.sharedHealth;
  }

  public double getMaxHealth() {
    return this.maxHealth;
  }

  public boolean isSlaughtering() {
    return this.slaughter;
  }

  public void fillSharedHealth() {
    this.sharedHealth = this.maxHealth;
    this.updateAllPlayerHealthBars(this.sharedHealth, false);
  }

  public void addSharedHealth(double amnt) {
    this.sharedHealth = Math.min(this.maxHealth, this.sharedHealth + amnt);
    this.updateAllPlayerHealthBars(this.sharedHealth, false);
  }

  public void subtractSharedHealth(double amnt, Entity p, DamageSource e) {
    if (this.isSlaughtering()) return;
    this.sharedHealth = this.sharedHealth - amnt;
    if (this.sharedHealth <= 0) {
      this.handleServerDeath(p, e);
      return;
    }
    this.updateAllPlayerHealthBars(this.sharedHealth, true);
  }

  public void handleServerDeath(Entity deadPlayer, DamageSource cause) {
    this.slaughter = true;
    String deadPlayerName = deadPlayer == null ? "Unknown player" : deadPlayer.getName();
    String killerName = "unknown";
    if (cause.getCausingEntity() != null)
      killerName = cause.getCausingEntity().getName();
    else if (cause != null)
      killerName = cause.getDamageType().getKey().getKey().replace('_', ' ').toLowerCase();
    final String trueKiller = killerName;
    this.plugin.getServer().getScheduler().runTask(plugin, () -> {
      for (var p : Bukkit.getOnlinePlayers())
        p.setHealth(0.0);
      Bukkit.broadcast(Component.text(String.format("%s died to %s", deadPlayerName, trueKiller)));
      this.slaughter = false;
     });
    this.fillSharedHealth();
  }

  private void updateAllPlayerHealthBars(double sharedHealth, boolean spawnParticle) {
    Bukkit.getOnlinePlayers().forEach(p -> {
      double health = this.calculatePlayerHealth(sharedHealth, p.getAttribute(Attribute.MAX_HEALTH).getValue());
      p.setHealth(health);
      if (spawnParticle) this.spawnParticles(p);
    });
  }

  private double calculatePlayerHealth(double sharedHealth, double maxHealth) {
    double fraction = sharedHealth / this.getMaxHealth();
    return Math.max(0.1, Math.min(fraction * maxHealth, maxHealth));
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
