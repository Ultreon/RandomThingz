package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.common.ModItems;
import com.ultreon.randomthingz.item.energy.EnergyStoringItem;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public final class ModModelProperties {
    private static final List<ItemModelProperty> deplayed = new ArrayList<>();

    public static void delayedRegister(Supplier<Item> item, String name, IItemPropertyGetter getter) {
        deplayed.add(new ItemModelProperty(item, name, getter));
    }

    public static void delayedRegister(Item item, String name, IItemPropertyGetter getter) {
        delayedRegister(() -> item, name, getter);
    }

    private ModModelProperties() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void register(FMLClientSetupEvent event) {
        register(ModItems.BATTERY, EnergyStoringItem.CHARGE, (stack, dimension, entity) ->
                EnergyStoringItem.getChargeRatio(stack));
        register(ModItems.MAGNET, EnergyStoringItem.CHARGE, (stack, dimension, entity) ->
                EnergyStoringItem.getChargeRatio(stack));

        for (ItemModelProperty property : deplayed) {
            register(property.item.get(), RandomThingz.rl(property.name), property.getter);
        }
    }

    private static void register(IItemProvider item, ResourceLocation property, IItemPropertyGetter getter) {
        ItemModelsProperties.registerProperty(item.asItem(), property, getter);
    }

    private static class ItemModelProperty {
        private final Supplier<Item> item;
        private final String name;
        private final IItemPropertyGetter getter;

        public ItemModelProperty(Supplier<Item> item, String name, IItemPropertyGetter getter) {
            this.item = item;
            this.name = name;
            this.getter = getter;
        }
    }
}
