package com.ultreon.randomthingz.world.gen.placement.common;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.world.gen.features.common.ModFeatures;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

@UtilityClass
public class ModPlacedFeatures {
//    public static final FeatureDecorator<ChanceDecoratorConfiguration> OIL_LAKE = register("oil_lake", ModFeatures.LAKE_OIL);
    public static final PlacedFeature EUCALYPTUS_TREE = register("eucalyptus_tree", ModFeatures.EUCALYPTUS_TREE.placed(RarityFilter.onAverageOnceEvery(5)));
    public static final PlacedFeature CHERRY_TREE = register("cherry_tree", ModFeatures.CHERRY_TREE.placed(RarityFilter.onAverageOnceEvery(10)));
    public static final PlacedFeature OIL_LAKE = register("oil_lake", ModFeatures.OIL_LAKE.placed(RarityFilter.onAverageOnceEvery(60)));
    public static final PlacedFeature OIL_SPRING = register("oil_spring", ModFeatures.OIL_SPRING.placed(RarityFilter.onAverageOnceEvery(115)));

    private static PlacedFeature register(String key, PlacedFeature placement) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, RandomThingz.res(key), placement);
    }
}
