package com.ultreon.randomthingz.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.entity.Moobloom;
import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Entity listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
@UtilityClass
public class EntityListener {
    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
        MobSpawnType spawnReason = event.getSpawnReason();
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType() == EntityType.COW && spawnReason == MobSpawnType.NATURAL) {
            BlockPos position = entity.blockPosition();
            Random rng = entity.getRandom();
            Level dimension = entity.level;
            if (dimension instanceof ServerLevel) {
                Biome biome = dimension.getBiome(position);

                if (biome.getRegistryName() == Biomes.FLOWER_FOREST.location()) {
                    if (rng.nextInt(10) < 9) {
                        replaceCows(event, position, (ServerLevel) dimension, biome);
                    }
                } else if (!biome.getGenerationSettings().features().isEmpty()) {
                    if (rng.nextInt(50) == 0) {
                        replaceCows(event, position, (ServerLevel) dimension, biome);
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void replaceCows(LivingSpawnEvent.CheckSpawn event, BlockPos position, ServerLevel dimension, Biome biome) {
        if (event.getSpawnReason() != MobSpawnType.NATURAL) return;

        LivingEntity entity = event.getEntityLiving();
        for (List<Supplier<PlacedFeature>> features : biome.getGenerationSettings().features()) {
            for (Supplier<PlacedFeature> featureSupplier : features) {
                PlacedFeature placedFeature = featureSupplier.get();
//                FeatureConfiguration config = placedFeature.config;
                Stream<ConfiguredFeature<?, ?>> configuredFeatures = placedFeature.getFeatures();
                configuredFeatures.forEach(configuredFeature -> {
                    FeatureConfiguration config = configuredFeature.config;
                    Feature<?> feature = configuredFeature.feature;
                    if (config instanceof RandomPatchConfiguration configuration && (feature instanceof VegetationPatchFeature || feature == Feature.RANDOM_PATCH)) {
                        PlacedFeature subPlacedFeature = configuration.feature().get();
                        Stream<ConfiguredFeature<?, ?>> subConfiguredFeatures = subPlacedFeature.getFeatures();
                        subConfiguredFeatures.forEach(configuredFeature1 -> {
                            if (configuredFeature1.feature instanceof SimpleBlockFeature &&
                                    configuredFeature1.config instanceof SimpleBlockConfiguration blockConfiguration &&
                                    blockConfiguration.toPlace() instanceof SimpleStateProvider stateProvider) {
                                BlockState state = stateProvider.getState(entity.getRandom(), position);
                                if (state == null) return;

                                Block block1 = state.getBlock();
                                Moobloom.Type type = Moobloom.Type.getFromBlock(block1);
                                CompoundTag dataTag = new CompoundTag();
                                dataTag.putInt("MoobloomType", type.getId());

                                entity.discard();
                                ModEntities.MOOBLOOM.getEntityType().spawn(dimension, dataTag, null, null, position, event.getSpawnReason(), false, false);
                            } else if (configuredFeature1.feature instanceof BlockColumnFeature &&
                                    configuredFeature1.config instanceof BlockColumnConfiguration blockColumnConfiguration) {
                                List<BlockColumnConfiguration.Layer> layers = blockColumnConfiguration.layers();
                                for (BlockColumnConfiguration.Layer layer : layers) {
                                    BlockState state = layer.state().getState(entity.getRandom(), position);
                                    if (state == null) continue;

                                    Block block1 = state.getBlock();
                                    Moobloom.Type type = Moobloom.Type.getFromBlock(block1);
                                    CompoundTag dataTag = new CompoundTag();
                                    dataTag.putInt("MoobloomType", type.getId());

                                    entity.discard();
                                    ModEntities.MOOBLOOM.getEntityType().spawn(dimension, dataTag, null, null, position, event.getSpawnReason(), false, false);
                                }
                            }
                        });
                    } else if (feature instanceof ChorusPlantFeature) {
                        Moobloom.Type type = Moobloom.Type.CHORUS;
                        CompoundTag dataTag = new CompoundTag();
                        dataTag.putInt("MoobloomType", type.getId());

                        entity.discard();
                        ModEntities.MOOBLOOM.getEntityType().spawn(dimension, dataTag, null, null, position, event.getSpawnReason(), false, false);
                    } else if (feature instanceof BambooFeature) {
                        Moobloom.Type type = Moobloom.Type.BAMBOO;
                        CompoundTag dataTag = new CompoundTag();
                        dataTag.putInt("MoobloomType", type.getId());

                        entity.discard();
                        ModEntities.MOOBLOOM.getEntityType().spawn(dimension, dataTag, null, null, position, event.getSpawnReason(), false, false);
                    }
                });
            }
        }
    }
}
