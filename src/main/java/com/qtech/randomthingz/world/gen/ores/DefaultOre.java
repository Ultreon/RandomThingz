package com.qtech.randomthingz.world.gen.ores;

import com.qsoftware.modlib.silentlib.block.IBlockProvider;
import com.qsoftware.modlib.silentutils.Lazy;
import com.qtech.randomthingz.commons.exceptions.InvalidNameException;
import com.qtech.randomthingz.item.common.ItemMaterial;
import com.qtech.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import com.qtech.randomthingz.world.gen.ores.configs.IOreConfig;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Handles ore blocks and default ore configs
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class DefaultOre implements IBlockProvider, IOre {
    private final Supplier<ItemMaterial> material;
    protected final DefaultOreConfig config;
    private final int hardness;
    private final int harvestLevel;
    private final Lazy<ConfiguredFeature<?, ?>> configuredFeature = Lazy.of(this::generate);

    private static final List<DefaultOre> VALUES = new ArrayList<>();

    private final String name;
    private final Map<Block, Block> oreGroundTypeMap = new HashMap<>();
    private Predicate<BiomeLoadingEvent> predicate;

    public DefaultOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config) {
        this(name, material, hardness, harvestLevel, config, (b) -> true);
    }

    public DefaultOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        this.material = material;
        this.config = config;
        this.hardness = hardness;
        this.harvestLevel = harvestLevel;
        this.predicate = predicate;

        // Enum backports.
        VALUES.add(this);
        this.name = name;

        if (!Pattern.compile("[a-z0-9_]").matcher(name).find()) {
            throw new InvalidNameException("Ore name is invalid.");
        }
    }

    @Override
    public ConfiguredFeature<?, ?> generate() {
        DefaultOreConfig config = this.config;
        int bottom = config.getMinHeight();
        if (config.getVeinSize() < 2) {
            return Feature.EMERALD_ORE
                    .withConfiguration(new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), this.asBlockState()))
                    .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                    .square()
                    .count(config.getVeinCount());
        }
        return Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, this.asBlockState(), config.getVeinSize()))
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(bottom, bottom, config.getMaxHeight())))
                .square()
                .count(config.getVeinCount());
    }

    @Deprecated
    public static List<DefaultOre> values() {
        return VALUES;
    }

    @Deprecated
    public String name() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public int getHardness() {
        return hardness;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public IOreConfig getOreConfig() {
        return config;
    }

    @Override
    public ConfiguredFeature<?, ?> getConfiguredFeature() {
        return configuredFeature.get();
    }

    @Override
    public Block getOre() {
        return asBlock();
    }

    @Override
    public BlockState getFeatureState() {
        return getOre().getDefaultState();
    }

    @Override
    public Collection<Block> getGroundTypes() {
        return oreGroundTypeMap.values();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Block asBlock() {
        return material.get().getOre().get();
    }

    @Override
    public Predicate<BiomeLoadingEvent> getBiomePredicate() {
        return predicate;
    }
}
