package com.qsoftware.forgemod.world.gen;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID)
public class EntitySpawns {
    @SubscribeEvent
    public static void spawnEntities(BiomeLoadingEvent event) {
        // Nether mobs
        if (event.getCategory() == Biome.Category.NETHER) {
            if (event.getName().getPath().equals("worldgen/biome/basalt_deltas") || event.getName().getPath().equals("basalt_deltas")) {
                List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
                monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.FIRE_CREEPER_ENTITY.get(), 13, 1, 3));
            }
        }

        // End mobs
        else if (event.getCategory() == Biome.Category.THEEND) {
            if (event.getName().getPath().equals("worldgen/biome/basalt_deltas") || event.getName().getPath().equals("basalt_deltas")) {
                List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
                monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.FIRE_CREEPER_ENTITY.get(), 13, 1, 3));
            }
        }

        // Overworld mobs
        else {
            if (event.getCategory() != Biome.Category.OCEAN && event.getCategory() != Biome.Category.RIVER && event.getCategory() != Biome.Category.BEACH) {
                List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
                if (event.getCategory() != Biome.Category.JUNGLE) {
                    creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.HOG_ENTITY.get(), 10, 2, 5));
                    creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.WRAT_HOG_ENTITY.get(), 4, 2, 5));
                }
            }
            if (event.getCategory() == Biome.Category.PLAINS
                    || event.getCategory() == Biome.Category.DESERT
                    || event.getCategory() == Biome.Category.FOREST
                    || event.getCategory() == Biome.Category.MESA
                    || event.getCategory() == Biome.Category.SAVANNA
                    || event.getCategory() == Biome.Category.EXTREME_HILLS) {
                List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
                creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.OX_ENTITY.get(), 8, 2, 5));
            }
            if (event.getCategory() == Biome.Category.RIVER) {
                List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
                creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.DUCK_ENTITY.get(), 11, 2, 5));
            }
            if (event.getCategory() == Biome.Category.DESERT) {
                List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
                creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.BISON_ENTITY.get(), 6, 2, 5));
            }
            if (event.getCategory() == Biome.Category.ICY) {
                List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
                monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.ICE_ENDERMAN_ENTITY.get(), 14, 1, 2));
            }
            if (event.getCategory() == Biome.Category.NETHER) {
                if (event.getName().getPath().equals("worldgen/biome/basalt_deltas") || event.getName().getPath().equals("basalt_deltas")) {
                    List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
                    monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.FIRE_CREEPER_ENTITY.get(), 13, 1, 3));
                }
            }
        }
    }
}
