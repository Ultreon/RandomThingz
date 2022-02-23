package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Ores {
    // Default ore.
    public static final ItemMaterialOre TIN = new ItemMaterialOre("tin", () -> ItemMaterial.TIN, 3, ToolRequirement.STONE, new DefaultOreConfig(16, 8, -40, 80));
    public static final ItemMaterialOre SILVER = new ItemMaterialOre("silver", () -> ItemMaterial.SILVER, 4, ToolRequirement.STONE, new DefaultOreConfig(4, 8, -64, 40));
    public static final ItemMaterialOre LEAD = new ItemMaterialOre("lead", () -> ItemMaterial.LEAD, 4, ToolRequirement.IRON, new DefaultOreConfig(4, 8, -64, 30));
    public static final ItemMaterialOre NICKEL = new ItemMaterialOre("nickel", () -> ItemMaterial.NICKEL, 5, ToolRequirement.STONE, new DefaultOreConfig(1, 6, -64, 24));
    public static final ItemMaterialOre PLATINUM = new ItemMaterialOre("platinum", () -> ItemMaterial.PLATINUM, 5, ToolRequirement.IRON, new DefaultOreConfig(2, 8, -25, 20));
    public static final ItemMaterialOre ZINC = new ItemMaterialOre("zinc", () -> ItemMaterial.ZINC, 3, ToolRequirement.STONE, new DefaultOreConfig(6, 8, -20, 60));
    public static final ItemMaterialOre BISMUTH = new ItemMaterialOre("bismuth", () -> ItemMaterial.BISMUTH, 3, ToolRequirement.STONE, new DefaultOreConfig(5, 8, -30, 48));
    public static final ItemMaterialOre BAUXITE = new ItemMaterialOre("bauxite", () -> ItemMaterial.ALUMINUM, 4, ToolRequirement.STONE, new DefaultOreConfig(6, 8, -37, 44));
    public static final ItemMaterialOre URANIUM = new ItemMaterialOre("uranium", () -> ItemMaterial.URANIUM, 6, ToolRequirement.IRON, new DefaultOreConfig(1, 4, -64, -44));

    // Gems
    public static final ItemMaterialOre RUBY = new ItemMaterialOre("ruby", () -> ItemMaterial.RUBY, 6, ToolRequirement.IRON, new DefaultOreConfig(1, 4, -64, 18));
    public static final ItemMaterialOre AMETHYST = new ItemMaterialOre("amethyst", () -> ItemMaterial.AMETHYST, 6, ToolRequirement.IRON, new DefaultOreConfig(1, 4, -64, -44));
    public static final ItemMaterialOre AQUAMARINE = new ItemMaterialOre("aquamarine", () -> ItemMaterial.AQUAMARINE, 6, ToolRequirement.IRON, new DefaultOreConfig(1, 6, -8, 16));
    public static final ItemMaterialOre SAPPHIRE = new ItemMaterialOre("amethyst", () -> ItemMaterial.SAPPHIRE, 6, ToolRequirement.IRON, new DefaultOreConfig(1, 4, -29, 2));
    public static final ItemMaterialOre AMBER = new ItemMaterialOre("amber", () -> ItemMaterial.AMBER, 6, ToolRequirement.IRON, new DefaultOreConfig(2, 4, -33, 8));
    public static final ItemMaterialOre PERIDOT = new ItemMaterialOre("peridot", () -> ItemMaterial.PERIDOT, 6, ToolRequirement.IRON, new DefaultOreConfig(4, 4, -8, 12));
    public static final ItemMaterialOre BERYL = new ItemMaterialOre("beryl", () -> ItemMaterial.BERYL, 6, ToolRequirement.IRON, new DefaultOreConfig(3, 4, -12, 18));
    public static final ItemMaterialOre TOPAZ = new ItemMaterialOre("topaz", () -> ItemMaterial.TOPAZ, 6, ToolRequirement.IRON, new DefaultOreConfig(2, 4, -42, -12));
    public static final ItemMaterialOre TANZANITE = new ItemMaterialOre("tanzanite", () -> ItemMaterial.TANZANITE, 6, ToolRequirement.IRON, new DefaultOreConfig(1, 4, -64, -36));
    public static final ItemMaterialOre MALACHITE = new ItemMaterialOre("malachite", () -> ItemMaterial.MALACHITE, 6, ToolRequirement.IRON, new DefaultOreConfig(4, 4, -64, -32));

    // Nether ore.
    public static final ItemMaterialNetherOre COBALT = new ItemMaterialNetherOre("cobalt", () -> ItemMaterial.COBALT, 64, ToolRequirement.NETHERITE, new DefaultOreConfig(48, 4, -64, 48));

    // Chanced ore.
    public static final ItemMaterialOre ULTRINIUM = new ItemMaterialOre("ultrinium", () -> ItemMaterial.ULTRINIUM, 512, ToolRequirement.COBALT, new DefaultOreConfig(1, 1, -64, 64, 128), (b) -> b.getClimate().temperature >= 1.0);
    public static final ItemMaterialOre INFINITY = new ItemMaterialOre("infinity", () -> ItemMaterial.INFINITY, 8192, ToolRequirement.ULTRINIUM, new DefaultOreConfig(1, 1, -64, 64, 4096), (b) -> b.getClimate().temperature >= 2.0);
}
