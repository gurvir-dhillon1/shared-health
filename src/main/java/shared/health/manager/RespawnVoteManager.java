package shared.health.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public class RespawnVoteManager {
    private final Set<UUID> votes = new HashSet<>();

    public void vote(Player p) {
        votes.add(p.getUniqueId());
    }

    public boolean hasMajority(int deadCount) {
        return votes.size() > deadCount / 2;
    }

    public void reset() {
        votes.clear();
    }
}
