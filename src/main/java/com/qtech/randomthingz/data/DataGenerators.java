package com.qtech.randomthingz.data;

import com.qtech.randomthingz.data.client.ModBlockStateProvider;
import com.qtech.randomthingz.data.client.ModItemModelProvider;
import com.qtech.randomthingz.data.loot.ModLootTableProvider;
import com.qtech.randomthingz.data.recipes.ModRecipesProvider;
import lombok.experimental.UtilityClass;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@UtilityClass
public final class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
//
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, event.getModContainer().getModId(), event.getExistingFileHelper());
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, event.getModContainer().getModId(), event.getExistingFileHelper()));
        gen.addProvider(new ModRecipesProvider(gen));
        gen.addProvider(new ModLootTableProvider(gen));

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
    }
}
