package com.ultreon.randomthingz.common.entity;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
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
            List<MobSpawnSettings.SpawnerData> monsterSpawns = event.getSpawns().getSpawner(MobCategory.MONSTER);
            monsterSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.FIRE_CREEPER.get(), 13, 1, 3));
            RandomThingz.LOGGER.debug("Added Fire Creeper to " + event.getName());
        }

        // Overworld mobs
        if (event.getCategory() == Biome.BiomeCategory.FOREST ||
                event.getCategory() == Biome.BiomeCategory.PLAINS ||
                event.getCategory() == Biome.BiomeCategory.JUNGLE ||
                event.getCategory() == Biome.BiomeCategory.NETHER ||
                event.getCategory() == Biome.BiomeCategory.SWAMP) {
            List<MobSpawnSettings.SpawnerData> creatureSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
            creatureSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.MOOBLOOM.get(), 4, 1, 3));
            RandomThingz.LOGGER.debug("Added Moobloom to " + event.getName());
        }
        if (event.getCategory() != Biome.BiomeCategory.OCEAN && event.getCategory() != Biome.BiomeCategory.RIVER && event.getCategory() != Biome.BiomeCategory.BEACH) {
            List<MobSpawnSettings.SpawnerData> creatureSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
            if (event.getCategory() != Biome.BiomeCategory.JUNGLE) {
                creatureSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.HOG.get(), 10, 2, 5));
                RandomThingz.LOGGER.debug("Added Hog to " + event.getName());
                creatureSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.WARTHOG.get(), 4, 2, 5));
                RandomThingz.LOGGER.debug("Added Warthog to " + event.getName());
            }
        }
        if (event.getCategory() == Biome.BiomeCategory.PLAINS
                || event.getCategory() == Biome.BiomeCategory.DESERT
                || event.getCategory() == Biome.BiomeCategory.FOREST
                || event.getCategory() == Biome.BiomeCategory.MESA
                || event.getCategory() == Biome.BiomeCategory.SAVANNA
                || event.getCategory() == Biome.BiomeCategory.EXTREME_HILLS) {
            List<MobSpawnSettings.SpawnerData> creatureSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
            creatureSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.OX.get(), 8, 2, 5));
            RandomThingz.LOGGER.debug("Added Ox to " + event.getName());
        }
        if (event.getCategory() == Biome.BiomeCategory.RIVER) {
            List<MobSpawnSettings.SpawnerData> creatureSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
            creatureSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.DUCK.get(), 11, 2, 5));
            RandomThingz.LOGGER.debug("Added Duck to " + event.getName());
        }
        if (event.getCategory() == Biome.BiomeCategory.DESERT) {
            List<MobSpawnSettings.SpawnerData> creatureSpawns = event.getSpawns().getSpawner(MobCategory.CREATURE);
            creatureSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.BISON.get(), 6, 2, 5));
            RandomThingz.LOGGER.debug("Added Bison to " + event.getName());
        }
        if (event.getCategory() == Biome.BiomeCategory.ICY) {
            List<MobSpawnSettings.SpawnerData> monsterSpawns = event.getSpawns().getSpawner(MobCategory.MONSTER);
            monsterSpawns.add(new MobSpawnSettings.SpawnerData(ModEntities.ICE_ENDERMAN.get(), 14, 1, 2));
            RandomThingz.LOGGER.debug("Added Ice Enderman to " + event.getName());
        }
    }
}
