package com.qtech.forgemod.client;

import com.qtech.forgemod.modules.items.ModItems;
import com.qtech.forgemod.modules.items.objects.energy.EnergyStoringItem;
import com.qtech.forgemod.util.ExceptionUtil;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("SameParameterValue")
public final class ModModelProperties {
    private ModModelProperties() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void register(FMLClientSetupEvent event) {
        register(ModItems.BATTERY, EnergyStoringItem.CHARGE, (stack, dimension, entity) ->
                EnergyStoringItem.getChargeRatio(stack));
        register(ModItems.MAGNET, EnergyStoringItem.CHARGE, (stack, dimension, entity) ->
                EnergyStoringItem.getChargeRatio(stack));
    }

    private static void register(IItemProvider item, ResourceLocation property, IItemPropertyGetter getter) {
        ItemModelsProperties.registerProperty(item.asItem(), property, getter);
    }
}
