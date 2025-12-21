package shared.health;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;

public class SharedHealthManager {
  private double sharedHealth;
  private final double maxHealth;

  public SharedHealthManager(double startingHealth) {
    this.maxHealth = startingHealth;
    this.sharedHealth = startingHealth;
  }

  public double getSharedHealth() {
    return this.sharedHealth;
  }

  public double getMaxHealth() {
    return this.maxHealth;
  }

  public void addSharedHealth(double amnt) {
    this.sharedHealth = Math.min(this.maxHealth, this.sharedHealth + amnt);
    this.updateAllPlayerHealthBars(this.sharedHealth);
  }

  public void subtractSharedHealth(double amnt) {
    this.sharedHealth = this.sharedHealth - amnt;
    if (this.sharedHealth <= 0) {
      this.killAllPlayers();
    }
    this.updateAllPlayerHealthBars(this.sharedHealth);
  }

  public void killAllPlayers() {
    Bukkit.getOnlinePlayers().forEach(p -> p.setHealth(0.0));
  }

  private void updateAllPlayerHealthBars(double sharedHealth) {
    Bukkit.getOnlinePlayers().forEach(p -> {
      double fraction = sharedHealth / this.getMaxHealth();
      double maxHealth = p.getAttribute(Attribute.MAX_HEALTH).getValue();
      double health = Math.max(0, Math.min(fraction * maxHealth, maxHealth));
      p.setHealth(health);
    });
  }
}
