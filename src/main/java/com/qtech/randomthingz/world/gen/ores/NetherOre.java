package com.qtech.randomthingz.world.gen.ores;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qtech.randomthingz.item.common.ItemMaterial;
import com.qtech.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class NetherOre extends DefaultOre {
    public NetherOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config) {
        super(name, material, hardness, harvestLevel, config);
    }

    public NetherOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        super(name, material, hardness, harvestLevel, config, predicate);
    }

    @Override
    public ConfiguredFeature<?, ?> generate() {
        DefaultOreConfig config = this.config;
        int bottom = config.getMinHeight();
        if (config.getVeinSize() <= 2) {
            return Feature.EMERALD_ORE
                    .withConfiguration(new ReplaceBlockConfig(Blocks.NETHERRACK.getDefaultState(), this.asBlockState()))
                    .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                    .square()
                    .count(config.getVeinCount());
        }
        return Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, this.asBlockState(), config.getVeinSize()))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                .square()
                .count(config.getVeinCount());
    }
}
