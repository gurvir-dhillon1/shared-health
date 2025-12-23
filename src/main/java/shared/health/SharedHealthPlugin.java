package shared.health;

import org.bukkit.plugin.java.JavaPlugin;

import shared.health.listener.SharedHealthListener;
import shared.health.manager.DeadManager;
import shared.health.manager.SharedHealthManager;

public class SharedHealthPlugin extends JavaPlugin {

    private SharedHealthManager healthManager;
    private DeadManager deadManager;

    @Override
    public void onEnable() {
        this.healthManager = new SharedHealthManager(20);
        this.deadManager = new DeadManager();
        SharedHealthListener sharedHealthListener = new SharedHealthListener(this.healthManager, this.deadManager);
        getServer().getPluginManager().registerEvents(sharedHealthListener, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("SharedHealthPlugin disabled");
    }
}