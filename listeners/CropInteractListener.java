package com.quickharvest.listeners;

import com.quickharvest.QuickHarvest;
import com.quickharvest.types.CropType;
import com.quickharvest.utils.ConfigManager;
import com.quickharvest.utils.CropManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CropInteractListener implements Listener {
    private final QuickHarvest plugin;
    private final ConfigManager config;

    public CropInteractListener(QuickHarvest plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCropInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        Block block = event.getClickedBlock();
        if (block == null) return;

        Optional<CropType> cropType = CropType.fromMaterial(block.getType());
        if (!cropType.isPresent()) return;
        if (!config.isCropEnabled(cropType.get().name())) return;

        if (CropManager.handleHarvest(event.getPlayer(), block, cropType.get(), config)) {
            event.setCancelled(true);
        }
    }
}