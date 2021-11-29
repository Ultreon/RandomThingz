package com.ultreon.randomthingz.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.entity.MoobloomEntity;
import com.ultreon.randomthingz.entity.common.ModEntities;
import com.ultreon.randomthingz.util.ListUtils;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.function.Supplier;

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
        SpawnReason spawnReason = event.getSpawnReason();
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType() == EntityType.COW && spawnReason == SpawnReason.NATURAL) {
            BlockPos position = entity.getPosition();
            Random rng = entity.getRNG();
            World dimension = entity.dimension;
            if (dimension instanceof ServerWorld) {
                Biome biome = dimension.getBiome(position);

                if (biome.getRegistryName() == Biomes.FLOWER_FOREST.getLocation()) {
                    if (rng.nextInt(10) < 9) {
                        replaceCows(event, position, (ServerWorld) dimension, biome);
                    }
                } else if (!biome.getGenerationSettings().getFeatures().isEmpty()) {
                    if (rng.nextInt(50) == 0) {
                        replaceCows(event, position, (ServerWorld) dimension, biome);
                    }
                }
            }
        }
    }

    private static void replaceCows(LivingSpawnEvent.CheckSpawn event, BlockPos position, ServerWorld dimension, Biome biome) {
        LivingEntity entity = event.getEntityLiving();
        Set<MoobloomEntity.Type> types = new HashSet<>();
        for (List<Supplier<ConfiguredFeature<?, ?>>> features : biome.getGenerationSettings().getFeatures()) {
            for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
                ConfiguredFeature<?, ?> configuredFeature = featureSupplier.get();
                IFeatureConfig config = configuredFeature.config;
                Feature<?> feature = configuredFeature.feature;
                if (config instanceof BlockClusterFeatureConfig) {
                    if (feature instanceof DefaultFlowersFeature || feature == Feature.RANDOM_PATCH) {
                        Set<Block> blocks1 = ((BlockClusterFeatureConfig) config).whitelist;
                        for (Block block : blocks1) {
                            MoobloomEntity.Type moobloomType = MoobloomEntity.Type.getFromBlock(block);
                            if (moobloomType != null) {
                                types.add(moobloomType);
                            }
                        }
                    }
                }
                if (feature instanceof ChorusPlantFeature) {
                    MoobloomEntity.Type moobloomType = MoobloomEntity.Type.getFromBlock(Blocks.CHORUS_FLOWER);
                    if (moobloomType != null) {
                        types.add(moobloomType);
                    }
                }
                if (feature instanceof BambooFeature) {
                    MoobloomEntity.Type moobloomType = MoobloomEntity.Type.getFromBlock(Blocks.BAMBOO);
                    if (moobloomType != null) {
                        types.add(moobloomType);
                    }
                }
            }
        }

        MoobloomEntity.Type type = ListUtils.choice(new ArrayList<>(types));
        CompoundNBT dataTag = new CompoundNBT();
        dataTag.putInt("MoobloomType", type.getId());

        entity.remove(false);
        ModEntities.MOOBLOOM.getEntityType().spawn(dimension, dataTag, null, null, position, SpawnReason.NATURAL, false, false);
    }
}
