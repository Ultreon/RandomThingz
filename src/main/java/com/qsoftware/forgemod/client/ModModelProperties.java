package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.init.ModItems;
import com.qsoftware.forgemod.objects.items.energy.EnergyStoringItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ModModelProperties {
    private ModModelProperties() {
    }

    public static void register(FMLClientSetupEvent event) {
        register(ModItems.BATTERY, EnergyStoringItem.CHARGE, (stack, world, entity) ->
                EnergyStoringItem.getChargeRatio(stack));
        register(ModItems.MAGNET, EnergyStoringItem.CHARGE, (stack, world, entity) ->
                EnergyStoringItem.getChargeRatio(stack));
    }

    private static void register(IItemProvider item, ResourceLocation property, IItemPropertyGetter getter) {
        ItemModelsProperties.registerProperty(item.asItem(), property, getter);
    }
}
