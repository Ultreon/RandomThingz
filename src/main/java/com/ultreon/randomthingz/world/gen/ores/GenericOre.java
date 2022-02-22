package com.ultreon.randomthingz.world.gen.ores;

import com.ultreon.modlib.silentutils.Lazy;
import com.ultreon.randomthingz.common.exceptions.InvalidNameException;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
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
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
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
public class GenericOre implements Ore {
    private static final List<GenericOre> VALUES = new ArrayList<>();

    protected final Supplier<Block> material;
    protected final DefaultOreConfig config;

    private final float hardness;
    private final float resistance;
    private final RuleTest ruleTest;
    private final ToolType toolType;
    private final ToolRequirement toolRequirement;
    private final String name;

    private final Lazy<PlacedFeature> placedFeature = Lazy.of(this::generate);

    private final Map<Block, Block> oreGroundTypeMap = new HashMap<>();
    private final Predicate<BiomeLoadingEvent> predicate;

    public GenericOre(String name, Supplier<Block> material, RuleTest ruleTest, float hardness, ToolType toolType, ToolRequirement toolRequirement, DefaultOreConfig config) {
        this(name, material, ruleTest, hardness, toolType, toolRequirement, config, (b) -> true);
    }

    public GenericOre(String name, Supplier<Block> material, RuleTest ruleTest, float hardness, float resistance, ToolType toolType, ToolRequirement toolRequirement, DefaultOreConfig config) {
        this(name, material, ruleTest, hardness, resistance, toolType, toolRequirement, config, (b) -> true);
    }

    public GenericOre(String name, Supplier<Block> material, RuleTest ruleTest, float hardness, ToolType toolType, ToolRequirement toolRequirement, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        this(name, material, ruleTest, hardness, 3, toolType, toolRequirement, config, predicate);
    }

    public GenericOre(String name, Supplier<Block> material, RuleTest ruleTest, float hardness, float resistance, ToolType toolType, ToolRequirement toolRequirement, DefaultOreConfig config, Predicate<BiomeLoadingEvent> predicate) {
        this.material = material;
        this.ruleTest = ruleTest;
        this.toolType = toolType;
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
                    List.of(OreConfiguration.target(ruleTest, material.get().defaultBlockState()))
            )).placed(CountPlacement.of(ConstantInt.of(config.getVeinCount())));
        }
        return Feature.ORE.configured(new OreConfiguration(
                List.of(OreConfiguration.target(ruleTest, material.get().defaultBlockState())),
                config.getVeinSize()
        )).placed(CountPlacement.of(ConstantInt.of(config.getVeinCount())), HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(config.getMinHeight()), VerticalAnchor.absolute(config.getMaxHeight()))), RarityFilter.onAverageOnceEvery(config.getSpawnChance()));
    }

    @Deprecated
    public static List<GenericOre> values() {
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
        return material.get();
    }

    @Override
    public Block getDeepslateOre() {
        return material.get();
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

    public ToolType getToolType() {
        return toolType;
    }

    @Override
    public ToolRequirement getToolRequirement() {
        return toolRequirement;
    }

    public RuleTest getRuleTest() {
        return ruleTest;
    }
}
