package com.qsoftware.forgemod.modules.worldgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.stream.Stream;

public class LakeOil extends Placement<ChanceConfig> {
   public LakeOil(Codec<ChanceConfig> codec) {
      super(codec);
   }

   public @NotNull Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ChanceConfig config, BlockPos pos) {
      if (rand.nextInt(config.chance) == 0) {
         int i = rand.nextInt(12) + pos.getX();
         int j = rand.nextInt(12) + pos.getZ();
         int k = rand.nextInt(helper.func_242891_a());
         return Stream.of(new BlockPos(i, k, j));
      } else {
         return Stream.empty();
      }
   }
}
