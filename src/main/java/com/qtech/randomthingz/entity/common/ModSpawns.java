package com.qtech.randomthingz.entity.common;

import com.qtech.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * Entity spawns class.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
@UtilityClass
public class ModSpawns {
    @SubscribeEvent
    public static void spawnEntities(BiomeLoadingEvent event) {
        // Nether mobs
        if (event.getName().getPath().equals("basalt_deltas")) {
            List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            monsterSpawns.add(new MobSpawnInfo.Spawners(ModEntities.FIRE_CREEPER.get(), 13, 1, 3));
            RandomThingz.LOGGER.debug("Added Fire Creeper to " + event.getName());
        }

        // Overworld mobs
        if (event.getCategory() == Biome.Category.FOREST ||
                event.getCategory() == Biome.Category.PLAINS ||
                event.getCategory() == Biome.Category.JUNGLE ||
                event.getCategory() == Biome.Category.NETHER ||
                event.getCategory() == Biome.Category.SWAMP) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(ModEntities.MOOBLOOM.get(), 4, 1, 3));
            RandomThingz.LOGGER.debug("Added Moobloom to " + event.getName());
        }
        if (event.getCategory() != Biome.Category.OCEAN && event.getCategory() != Biome.Category.RIVER && event.getCategory() != Biome.Category.BEACH) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            if (event.getCategory() != Biome.Category.JUNGLE) {
                creatureSpawns.add(new MobSpawnInfo.Spawners(ModEntities.HOG.get(), 10, 2, 5));
                RandomThingz.LOGGER.debug("Added Hog to " + event.getName());
                creatureSpawns.add(new MobSpawnInfo.Spawners(ModEntities.WARTHOG.get(), 4, 2, 5));
                RandomThingz.LOGGER.debug("Added Warthog to " + event.getName());
            }
        }
        if (event.getCategory() == Biome.Category.PLAINS
                || event.getCategory() == Biome.Category.DESERT
                || event.getCategory() == Biome.Category.FOREST
                || event.getCategory() == Biome.Category.MESA
                || event.getCategory() == Biome.Category.SAVANNA
                || event.getCategory() == Biome.Category.EXTREME_HILLS) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(ModEntities.OX.get(), 8, 2, 5));
            RandomThingz.LOGGER.debug("Added Ox to " + event.getName());
        }
        if (event.getCategory() == Biome.Category.RIVER) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(ModEntities.DUCK.get(), 11, 2, 5));
            RandomThingz.LOGGER.debug("Added Duck to " + event.getName());
        }
        if (event.getCategory() == Biome.Category.DESERT) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(ModEntities.BISON.get(), 6, 2, 5));
            RandomThingz.LOGGER.debug("Added Bison to " + event.getName());
        }
        if (event.getCategory() == Biome.Category.ICY) {
            List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            monsterSpawns.add(new MobSpawnInfo.Spawners(ModEntities.ICE_ENDERMAN.get(), 14, 1, 2));
            RandomThingz.LOGGER.debug("Added Ice Enderman to " + event.getName());
        }
    }
}
