package com.ultreon.randomthingz.world.gen.placement.common;

import com.ultreon.randomthingz.world.gen.placement.LakeOil;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

@SuppressWarnings("deprecation")
@UtilityClass
public class ModPlacements {
    public static final FeatureDecorator<ChanceDecoratorConfiguration> OIL_LAKE = register("oil_lake", new LakeOil(ChanceDecoratorConfiguration.CODEC));

    private static <T extends DecoratorConfiguration, G extends FeatureDecorator<T>> G register(String key, G placement) {
        return Registry.register(Registry.DECORATOR, key, placement);
    }
}
