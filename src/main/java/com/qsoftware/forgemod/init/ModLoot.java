package com.qsoftware.forgemod.init;

import com.google.common.collect.ImmutableList;
import com.qsoftware.forgemod.QForgeMod;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID)
public class ModLoot {
	private ModLoot() {}

//	private static final List<String> CHEST_TABLES = ImmutableList.of(
//			"abandoned_mineshaft", "desert_pyramid", "end_city_treasure", "igloo_chest", "jungle_temple",
//			"nether_bridge", "simple_dungeon", "stronghold_corridor", "stronghold_crossing", "stronghold_library",
//			"village_blacksmith");

	private static final List<String> ENTITY_TABLES = ImmutableList.of(
			"enderman");

	@SuppressWarnings("unused") //used in event
	@SubscribeEvent
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

	private static LootPool getInjectPool(String entryName) {
		return LootPool.builder().addEntry(getInjectEntry(entryName)).bonusRolls(0, 1).name("xreliquary_inject_pool").build();
	}

	private static LootEntry.Builder<?> getInjectEntry(String name) {
		return TableLootEntry.builder(new ResourceLocation(QForgeMod.MOD_ID, "inject/" + name)).weight(1);
	}
}