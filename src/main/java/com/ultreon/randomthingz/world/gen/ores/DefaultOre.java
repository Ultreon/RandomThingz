package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.modlib.embedded.silentlib.block.IBlockProvider;
import com.ultreon.modlib.embedded.silentutils.Lazy;
import com.ultreon.randomthingz.common.exceptions.InvalidNameException;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import com.ultreon.randomthingz.world.gen.ores.configs.IOreConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
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
    private final Predicate<BiomeLoadingEvent> predicate;
    private float resistance;

    public DefaultOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config) {
        this(name, material, hardness, harvestLevel, config, (b) -> true);
    }

    public DefaultOre(String name, Supplier<ItemMaterial> material, int hardness, int resistance, int harvestLevel, DefaultOreConfig config) {
        this(name, material, hardness, resistance, harvestLevel, config, (b) -> true);
    }

    public DefaultOre(String name, Supplier<ItemMaterial> material, int hardness, int harvestLevel, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        this(name, material, hardness, 3, harvestLevel, config, predicate);
    }

    public DefaultOre(String name, Supplier<ItemMaterial> material, int hardness, int resistance, int harvestLevel, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
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
                    .configured(new ReplaceBlockConfiguration(Blocks.STONE.defaultBlockState(), this.asBlockState()))
                    .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, config.getMaxHeight())))
                    .squared()
                    .count(config.getVeinCount());
        }
        return Feature.ORE
                .configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, this.asBlockState(), config.getVeinSize()))
                .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, config.getMaxHeight())))
                .squared()
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
        return getOre().defaultBlockState();
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

    @Override
    public float getResistance() {
        return resistance;
    }
}
