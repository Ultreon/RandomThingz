package com.ultreon.randomthingz.server.loot;

import com.google.common.collect.ImmutableList;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;

import java.util.List;

@SuppressWarnings("ALL")
@Deprecated(forRemoval = true)
public class ModLoot {
    private static final List<String> ENTITY_TABLES = ImmutableList.of(
            "enderman", "chicken", "pig", "sheep");

//	private static final List<String> CHEST_TABLES = ImmutableList.of(
//			"abandoned_mineshaft", "desert_pyramid", "end_city_treasure", "igloo_chest", "jungle_temple",
//			"nether_bridge", "simple_dungeon", "stronghold_corridor", "stronghold_crossing", "stronghold_library",
//			"village_blacksmith");

    @Deprecated(forRemoval = true)
    private ModLoot() {
        throw ExceptionUtil.utilityConstructor();
    }

    @Deprecated(forRemoval = true)
    public static void lootLoad(LootTableLoadEvent evt) {
//		String chestsPrefix = "minecraft:chests/";
        String entitiesPrefix = "minecraft:entities/";
        String name = evt.getName().toString();

        if (/*(CHEST_TABLES.contains(name.substring(chestsPrefix.length())))
				|| */(ENTITY_TABLES.contains(name.substring(entitiesPrefix.length())))) {
            String file = name.substring("minecraft:".length());
            evt.getTable().addPool(getInjectPool(file));
        }
    }

    @Deprecated(forRemoval = true)
    private static LootPool getInjectPool(String entryName) {
        return LootPool.lootPool().add(getInjectEntry(entryName)).setBonusRolls(UniformGenerator.between(0, 1)).name("randomthingz_inject_pool").build();
    }

    @Deprecated(forRemoval = true)
    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name) {
        return LootTableReference.lootTableReference(new ResourceLocation(RandomThingz.MOD_ID, "inject/" + name)).setWeight(1);
    }
}
