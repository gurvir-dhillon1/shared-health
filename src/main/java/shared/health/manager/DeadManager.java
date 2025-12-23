package shared.health.manager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public class DeadManager {
  private Set<UUID> deadPlayers = new HashSet<>();
  
  public DeadManager() {
    this.deadPlayers = new HashSet<>();
  }

  public Set<UUID> getDeadPlayers() {
    return Collections.unmodifiableSet(this.deadPlayers);
  }

  public boolean isPlayerDead(Player p) {
    return this.deadPlayers.contains(p.getUniqueId());
  }

  public void addDeadPlayer(Player p) {
    deadPlayers.add(p.getUniqueId());
  }

  public void removeDeadPlayer(Player p) {
    deadPlayers.remove(p.getUniqueId());
  }

  public boolean areAllAlive() {
    if (this.deadPlayers.size() == 0)
      return true;
    return false;
  }
}
