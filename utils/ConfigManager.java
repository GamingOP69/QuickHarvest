package com.quickharvest.utils;

import com.quickharvest.QuickHarvest;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final QuickHarvest plugin;
    private final Map<String, Boolean> cropSettings = new HashMap<>();

    public ConfigManager(QuickHarvest plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        
        cropSettings.clear();
        for (String crop : config.getConfigurationSection("crops").getKeys(false)) {
            cropSettings.put(crop.toUpperCase(), config.getBoolean("crops." + crop));
        }
    }

    public boolean isCropEnabled(String cropType) {
        return cropSettings.getOrDefault(cropType, false);
    }

    public boolean requireSneaking() {
        return plugin.getConfig().getBoolean("require-sneaking", false);
    }

    public boolean isCreativeAllowed() {
        return plugin.getConfig().getBoolean("creative-mode", false);
    }

    public String getNoPermissionMessage() {
        return plugin.getConfig().getString("messages.no-permission", "&cNo permission!");
    }
}