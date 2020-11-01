package com.qsoftware.forgemod.init;

import jdk.nashorn.internal.ir.annotations.Ignore;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class FeatureInit {
    public static final ConfiguredFeature<?, ?> ORE_RUBY = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Features.States.DIAMOND_ORE, 8)).range(16).square();

    public static void registerFeatures() {
        Class<FeatureInit> clazz = FeatureInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Block.class.isAssignableFrom(field.getType())) {
                ConfiguredFeature<?, ?> configuredFeature;

                // Get field value.
                try666r6 {
                    configuredFeature = (ConfiguredFeature<?, ?>) field.get(null);
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field: " + field.getName().toLowerCase(), t);
                }

                // Register feature.
                try {
                    Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, field.getName().toLowerCase(), configuredFeature);
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when registering configured feature: " + field.getName().toLowerCase(), t);
                }
            }
        }
    }
}
