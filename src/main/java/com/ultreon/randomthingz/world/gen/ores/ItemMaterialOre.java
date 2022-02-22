package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.modlib.silentutils.Lazy;
import com.ultreon.randomthingz.common.exceptions.InvalidNameException;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.world.gen.ores.configs.DefaultOreConfig;
import com.ultreon.randomthingz.world.gen.ores.configs.OreConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

/**
 * Handles ore blocks and default ore configs
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemMaterialOre implements Ore {
    protected final Supplier<ItemMaterial> material;
    protected final DefaultOreConfig config;
    private final int hardness;
    private final int resistance;
    private final Lazy<PlacedFeature> placedFeature = Lazy.of(this::generate);

    private static final List<ItemMaterialOre> VALUES = new ArrayList<>();

    private final String name;
    private final Map<Block, Block> oreGroundTypeMap = new HashMap<>();
    private final Predicate<BiomeLoadingEvent> predicate;
    private final ToolRequirement toolRequirement;

    public ItemMaterialOre(String name, Supplier<ItemMaterial> material, int hardness, ToolRequirement toolRequirement, DefaultOreConfig config) {
        this(name, material, hardness, toolRequirement, config, (b) -> true);
    }

    public ItemMaterialOre(String name, Supplier<ItemMaterial> material, int hardness, int resistance, ToolRequirement toolRequirement, DefaultOreConfig config) {
        this(name, material, hardness, resistance, toolRequirement, config, (b) -> true);
    }

    public ItemMaterialOre(String name, Supplier<ItemMaterial> material, int hardness, ToolRequirement toolRequirement, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        this(name, material, hardness, 3, toolRequirement, config, predicate);
    }

    public ItemMaterialOre(String name, Supplier<ItemMaterial> material, int hardness, int resistance, ToolRequirement toolRequirement, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        this.material = material;
        this.config = config;
        this.hardness = hardness;
        this.resistance = resistance;
        this.toolRequirement = toolRequirement;
        this.predicate = predicate;

        // Enum backports.
        VALUES.add(this);
        this.name = name;

        if (!Pattern.compile("[a-z0-9_]").matcher(name).find()) {
            throw new InvalidNameException("Ore name is invalid.");
        }
    }

    @Override
    public PlacedFeature generate() {
        DefaultOreConfig config = this.config;
        if (config.getVeinSize() < 2) {
            return Feature.REPLACE_SINGLE_BLOCK.configured(new ReplaceBlockConfiguration(
                    List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, material.get().getStoneOre().orElseThrow().defaultBlockState()),
                            OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, material.get().getDeepslateOre().orElseThrow().defaultBlockState())
                    )
            )).placed(CountPlacement.of(ConstantInt.of(config.getVeinCount())));
        }
        return Feature.ORE.configured(new OreConfiguration(
                List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, material.get().getStoneOre().orElseThrow().defaultBlockState()),
                        OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, material.get().getDeepslateOre().orElseThrow().defaultBlockState())
                ), config.getVeinSize()
        )).placed(CountPlacement.of(ConstantInt.of(config.getVeinCount())), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(config.getMinHeight()), VerticalAnchor.absolute(config.getMaxHeight()))), RarityFilter.onAverageOnceEvery(config.getSpawnChance()));
    }

    public static List<ItemMaterialOre> values() {
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
    public float getHardness() {
        return hardness;
    }

    @Override
    public float getResistance() {
        return resistance;
    }

    @Override
    public OreConfig getOreConfig() {
        return config;
    }

    @Override
    public PlacedFeature getPlacedFeature() {
        return placedFeature.get();
    }

    @Override
    public Block getStoneOre() {
        return material.get().getStoneOre().orElseThrow();
    }

    @Override
    public Block getDeepslateOre() {
        return material.get().getDeepslateOre().orElseThrow();
    }

    @Override
    public BlockState getStoneFeatureState() {
        return getStoneOre().defaultBlockState();
    }

    @Override
    public BlockState getDeepslateFeatureState() {
        return getDeepslateOre().defaultBlockState();
    }

    @Override
    public Collection<Block> getGroundTypes() {
        return oreGroundTypeMap.values();
    }

    @Override
    public Predicate<BiomeLoadingEvent> getBiomePredicate() {
        return predicate;
    }

    @Override
    public ToolRequirement getToolRequirement() {
        return toolRequirement;
    }
}
