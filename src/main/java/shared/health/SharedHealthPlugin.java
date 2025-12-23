package shared.health;

import org.bukkit.plugin.java.JavaPlugin;

import shared.health.listener.SharedHealthListener;
import shared.health.manager.SharedHealthManager;

public class SharedHealthPlugin extends JavaPlugin {

    private SharedHealthManager healthManager;

    @Override
    public void onEnable() {
        this.healthManager = new SharedHealthManager(this, 20);
        SharedHealthListener sharedHealthListener = new SharedHealthListener(this.healthManager);
        getServer().getPluginManager().registerEvents(sharedHealthListener, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("SharedHealthPlugin disabled");
    }
}