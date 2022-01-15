package com.ultreon.randomthingz.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.entity.MoobloomEntity;
import com.ultreon.randomthingz.util.ListUtils;
import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
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

    private static void replaceCows(LivingSpawnEvent.CheckSpawn event, BlockPos position, ServerLevel dimension, Biome biome) {
        LivingEntity entity = event.getEntityLiving();
        Set<MoobloomEntity.Type> types = new HashSet<>();
        for (List<Supplier<ConfiguredFeature<?, ?>>> features : biome.getGenerationSettings().features()) {
            for (Supplier<ConfiguredFeature<?, ?>> featureSupplier : features) {
                ConfiguredFeature<?, ?> configuredFeature = featureSupplier.get();
                FeatureConfiguration config = configuredFeature.config;
                Feature<?> feature = configuredFeature.feature;
                if (config instanceof RandomPatchConfiguration) {
                    if (feature instanceof DefaultFlowerFeature || feature == Feature.RANDOM_PATCH) {
                        Set<Block> blocks1 = ((RandomPatchConfiguration) config).whitelist;
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
        CompoundTag dataTag = new CompoundTag();
        dataTag.putInt("MoobloomType", type.getId());

        entity.remove(false);
        ModEntities.MOOBLOOM.getEntityType().spawn(dimension, dataTag, null, null, position, MobSpawnType.NATURAL, false, false);
    }
}
