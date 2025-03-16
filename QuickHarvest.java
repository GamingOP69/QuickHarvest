package com.quickharvest;

import com.quickharvest.listeners.CropInteractListener;
import com.quickharvest.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickHarvest extends JavaPlugin {
    private ConfigManager configManager;
    
    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        new CropInteractListener(this);
        getCommand("quickharvest").setExecutor(new ReloadCommand(this));
        getLogger().info("QuickHarvest v" + getDescription().getVersion() + " enabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}