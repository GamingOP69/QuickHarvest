package com.quickharvest.types;

import org.bukkit.Material;
import java.util.EnumSet;
import java.util.Optional;

public enum CropType {
    WHEAT(Material.WHEAT, Material.WHEAT_SEEDS, 7),
    CARROTS(Material.CARROTS, Material.CARROT, 7),
    POTATOES(Material.POTATOES, Material.POTATO, 7),
    BEETROOT(Material.BEETROOTS, Material.BEETROOT_SEEDS, 3),
    NETHER_WART(Material.NETHER_WART, Material.NETHER_WART, 3);

    private final Material cropMaterial;
    private final Material seedMaterial;
    private final int maxAge;

    CropType(Material cropMaterial, Material seedMaterial, int maxAge) {
        this.cropMaterial = cropMaterial;
        this.seedMaterial = seedMaterial;
        this.maxAge = maxAge;
    }

    public static Optional<CropType> fromMaterial(Material material) {
        return EnumSet.allOf(CropType.class).stream()
                .filter(type -> type.cropMaterial == material)
                .findFirst();
    }

    // Getters
    public Material getCropMaterial() { return cropMaterial; }
    public Material getSeedMaterial() { return seedMaterial; }
    public int getMaxAge() { return maxAge; }
}