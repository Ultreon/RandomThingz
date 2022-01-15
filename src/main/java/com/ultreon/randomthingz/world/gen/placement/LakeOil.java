package com.ultreon.randomthingz.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.stream.Stream;

public class LakeOil extends FeatureDecorator<ChanceDecoratorConfiguration> {
    public LakeOil(Codec<ChanceDecoratorConfiguration> codec) {
        super(codec);
    }

    public @NotNull Stream<BlockPos> getPositions(DecorationContext helper, Random rand, ChanceDecoratorConfiguration config, BlockPos pos) {
        if (rand.nextInt(config.chance) == 0) {
            int i = rand.nextInt(12) + pos.getX();
            int j = rand.nextInt(12) + pos.getZ();
            int k = rand.nextInt(helper.getGenDepth());
            return Stream.of(new BlockPos(i, k, j));
        } else {
            return Stream.empty();
        }
    }
}
