package shared.health;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeadManager {
  private HashSet<UUID> deadPlayers;
  
  public DeadManager() {
    this.deadPlayers = new HashSet<>();
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
