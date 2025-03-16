package com.quickharvest.utils;

import com.quickharvest.types.CropType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class CropManager {
    public static boolean handleHarvest(Player player, Block block, CropType cropType, ConfigManager config) {
        // Check permissions
        if (!player.hasPermission("quickharvest.use")) return false;
        
        // Check creative mode
        if (player.getGameMode() == GameMode.CREATIVE && !config.isCreativeAllowed()) return false;
        
        // Check sneaking requirement
        if (config.requireSneaking() && !player.isSneaking()) return false;

        // Harvest logic
        Ageable ageable = (Ageable) block.getBlockData();
        if (ageable.getAge() == cropType.getMaxAge()) {
            processHarvest(player, block, cropType);
            return true;
        }
        return false;
    }

    private static void processHarvest(Player player, Block block, CropType cropType) {
        List<ItemStack> drops = new ArrayList<>(block.getDrops());
        ItemStack seedItem = findSeedItem(drops, cropType.getSeedMaterial());

        if (seedItem != null && seedItem.getAmount() > 0) {
            Location location = block.getLocation();
            
            // Remove one seed
            seedItem.setAmount(seedItem.getAmount() - 1);
            
            // Update block
            block.setType(Material.AIR);
            block.setType(cropType.getCropMaterial());
            Ageable newAgeable = (Ageable) block.getBlockData();
            newAgeable.setAge(0);
            block.setBlockData(newAgeable);

            // Give drops
            giveDrops(player, drops, location);
        }
    }

    private static ItemStack findSeedItem(List<ItemStack> drops, Material seedMaterial) {
        return drops.stream()
                .filter(item -> item.getType() == seedMaterial)
                .findFirst()
                .orElse(null);
    }

    private static void giveDrops(Player player, List<ItemStack> drops, Location location) {
        drops.removeIf(item -> item.getAmount() <= 0);
        
        // Add to inventory or drop
        for (ItemStack item : drops) {
            player.getInventory().addItem(item).values()
                    .forEach(leftover -> player.getWorld().dropItemNaturally(location, leftover));
        }
    }
}