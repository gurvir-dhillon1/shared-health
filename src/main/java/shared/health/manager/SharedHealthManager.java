package shared.health.manager;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

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
    this.updateAllPlayerHealthBars(this.sharedHealth);
  }

  public void addSharedHealth(double amnt) {
    this.sharedHealth = Math.min(this.maxHealth, this.sharedHealth + amnt);
    this.updateAllPlayerHealthBars(this.sharedHealth);
  }

  public void subtractSharedHealth(double amnt, Entity p, DamageSource e) {
    this.sharedHealth = this.sharedHealth - amnt;
    if (this.sharedHealth <= 0) {
      this.handleServerDeath(p, e);
    }
    this.updateAllPlayerHealthBars(this.sharedHealth);
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
      Bukkit.broadcastMessage(String.format("%s died to %s", deadPlayerName, trueKiller));
     });
    this.slaughter = false;
    this.fillSharedHealth();
  }

  private void updateAllPlayerHealthBars(double sharedHealth) {
    Bukkit.getOnlinePlayers().forEach(p -> {
      double fraction = sharedHealth / this.getMaxHealth();
      double maxHealth = p.getAttribute(Attribute.MAX_HEALTH).getValue();
      double health = Math.max(0.1, Math.min(fraction * maxHealth, maxHealth));
      p.setHealth(health);
    });
  }
}
