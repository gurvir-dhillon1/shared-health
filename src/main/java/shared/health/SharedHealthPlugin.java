package shared.health;

import org.bukkit.plugin.java.JavaPlugin;

import shared.health.listener.SharedHealthListener;

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