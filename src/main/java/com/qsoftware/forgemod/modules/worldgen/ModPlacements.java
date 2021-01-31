package com.qsoftware.forgemod.modules.worldgen;

import com.qsoftware.forgemod.modules.worldgen.placement.LakeOil;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.LakeWater;
import net.minecraft.world.gen.placement.Placement;

@SuppressWarnings("deprecation")
public class ModPlacements {
    public static final Placement<ChanceConfig> OIL_LAKE = register("oil_lake", new LakeOil(ChanceConfig.CODEC));

    private static <T extends IPlacementConfig, G extends Placement<T>> G register(String key, G placement) {
        return Registry.register(Registry.DECORATOR, key, placement);
    }
}
