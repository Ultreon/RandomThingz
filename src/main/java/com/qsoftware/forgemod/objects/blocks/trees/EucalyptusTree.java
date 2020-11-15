package com.qsoftware.forgemod.objects.blocks.trees;

import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.forgemod.init.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;
import java.util.Random;

public class EucalyptusTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(@NotNull Random randomIn, boolean largeHive) {
        return ModFeatures.EUCALYPTUS_TREE;
    }
}
