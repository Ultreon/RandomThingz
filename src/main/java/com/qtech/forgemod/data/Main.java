package com.qtech.forgemod.data;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.data.client.ModBlockStateProvider;
import com.qtech.forgemod.data.client.ModItemModelProvider;
import com.qtech.forgemod.data.loot.ModLootTableProvider;
import com.qtech.forgemod.data.recipes.ModRecipesProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.registry.Bootstrap;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModWorkManager;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class Main {
    private static GatherDataEvent.DataGeneratorConfig dataGeneratorConfig;
    private static Set<String> mods = new HashSet<>();
    private static Path path = Paths.get("./src/main/generated");
    private static ExistingFileHelper existingFileHelper;

    public static void main(String[] args) {

        Bootstrap.register();
        dataGeneratorConfig = new GatherDataEvent.DataGeneratorConfig(mods, path, null, true, true, true, true, true, false);
        ModLoader.get().gatherAndInitializeMods(ModWorkManager.syncExecutor(), ModWorkManager.parallelExecutor(), ()->{});
        //If we aren't generating data for forge, automatically add forge as an existing so mods can access forge's data
        mods.add(QForgeMod.modId);
        existingFileHelper = new ExistingFileHelper(Collections.singletonList(Paths.get("src/main/resources")), new HashSet<>(), true);
        GatherDataEvent event = new GatherDataEvent(null, dataGeneratorConfig.makeGenerator(p -> dataGeneratorConfig.isFlat() ? p : p.resolve(QForgeMod.modId), true), dataGeneratorConfig, existingFileHelper);
        DataGenerators.gatherData(event);
        dataGeneratorConfig.runAll();
    }
}
