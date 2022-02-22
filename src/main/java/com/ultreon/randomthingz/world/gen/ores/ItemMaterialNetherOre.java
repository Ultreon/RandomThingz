package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.minecraft.data.worldgen.features.OreFeatures.NETHER_ORE_REPLACEABLES;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class ItemMaterialNetherOre extends ItemMaterialOre {
    public ItemMaterialNetherOre(String name, Supplier<ItemMaterial> material, int hardness, ToolRequirement toolRequirement, DefaultOreConfig config) {
        super(name, material, hardness, toolRequirement, config);
    }

    public ItemMaterialNetherOre(String name, Supplier<ItemMaterial> material, int hardness, ToolRequirement toolRequirement, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, toolRequirement, config, predicate);
    }

    @Override
    public PlacedFeature generate() {
        DefaultOreConfig config = this.config;
        int bottom = config.getMinHeight();
        if (config.getVeinSize() <= 2) {
            return Feature.REPLACE_SINGLE_BLOCK.configured(new ReplaceBlockConfiguration(
                    List.of(OreConfiguration.target(NETHER_ORE_REPLACEABLES, material.get().getStoneOre().orElseThrow().defaultBlockState()))
            )).placed(RarityFilter.onAverageOnceEvery(config.getSpawnChance()));
        }
        return Feature.ORE.configured(new OreConfiguration(
                List.of(OreConfiguration.target(NETHER_ORE_REPLACEABLES, material.get().getStoneOre().orElseThrow().defaultBlockState())),
                config.getVeinSize()
        )).placed(RarityFilter.onAverageOnceEvery(config.getSpawnChance()));
    }
}
