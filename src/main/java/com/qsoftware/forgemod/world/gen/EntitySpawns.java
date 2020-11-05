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

/**
 * Entity spawns class.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID)
public class EntitySpawns {
    @SubscribeEvent
    public static void spawnEntities(BiomeLoadingEvent event) {
        // Nether mobs
        if (event.getName().getPath().equals("basalt_deltas")) {
            List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.FIRE_CREEPER.get(), 13, 1, 3));
            QForgeUtils.LOGGER.debug("Added Fire Creeper to " + event.getName());
        }

        // Overworld mobs
        if (event.getName().getPath().equals("flower_forest")) {
            List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.MOOBLOOM.get(), 4, 2, 5));
            QForgeUtils.LOGGER.debug("Added Moobloom to " + event.getName());
        }
        if (event.getCategory() != Biome.Category.OCEAN && event.getCategory() != Biome.Category.RIVER && event.getCategory() != Biome.Category.BEACH) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            if (event.getCategory() != Biome.Category.JUNGLE) {
                creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.HOG.get(), 10, 2, 5));
                QForgeUtils.LOGGER.debug("Added Hog to " + event.getName());
                creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.WARTHOG.get(), 4, 2, 5));
                QForgeUtils.LOGGER.debug("Added Warthog to " + event.getName());
            }
        }
        if (event.getCategory() == Biome.Category.PLAINS
                || event.getCategory() == Biome.Category.DESERT
                || event.getCategory() == Biome.Category.FOREST
                || event.getCategory() == Biome.Category.MESA
                || event.getCategory() == Biome.Category.SAVANNA
                || event.getCategory() == Biome.Category.EXTREME_HILLS) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.OX.get(), 8, 2, 5));
            QForgeUtils.LOGGER.debug("Added Ox to " + event.getName());
        }
        if (event.getCategory() == Biome.Category.RIVER) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.DUCK.get(), 11, 2, 5));
            QForgeUtils.LOGGER.debug("Added Duck to " + event.getName());
        }
        if (event.getCategory() == Biome.Category.DESERT) {
            List<MobSpawnInfo.Spawners> creatureSpawns = event.getSpawns().getSpawner(EntityClassification.CREATURE);
            creatureSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.BISON.get(), 6, 2, 5));
            QForgeUtils.LOGGER.debug("Added Bison to " + event.getName());
        }
        if (event.getCategory() == Biome.Category.ICY) {
            List<MobSpawnInfo.Spawners> monsterSpawns = event.getSpawns().getSpawner(EntityClassification.MONSTER);
            monsterSpawns.add(new MobSpawnInfo.Spawners(EntityTypeInit.ICE_ENDERMAN.get(), 14, 1, 2));
            QForgeUtils.LOGGER.debug("Added Ice Enderman to " + event.getName());
        }
    }
}
