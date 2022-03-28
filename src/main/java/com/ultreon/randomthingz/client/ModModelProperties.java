package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.init.ModItems;
import com.ultreon.randomthingz.item.type.EnergyStoringItem;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings({"SameParameterValue", "deprecation"})
public final class ModModelProperties {
    private ModModelProperties() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void register(FMLClientSetupEvent event) {
        register(ModItems.BATTERY, EnergyStoringItem.CHARGE, (stack, level, entity, i) ->
                EnergyStoringItem.getChargeRatio(stack));
        register(ModItems.MAGNET, EnergyStoringItem.CHARGE, (stack, level, entity, i) ->
                EnergyStoringItem.getChargeRatio(stack));
    }

    private static void register(ItemLike item, ResourceLocation property, ItemPropertyFunction getter) {
        ItemProperties.register(item.asItem(), property, getter);
    }
}
