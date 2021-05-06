package com.qtech.forgemod.init;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.item.common.ModItems;
import com.qtech.forgemod.item.spawnegg.CustomSpawnEggItem;
import com.qsoftware.modlib.silentlib.registry.EntityTypeRegistryObject;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

import java.util.function.Supplier;

/**
 * @author Qboi123
 * @deprecated Use {@linkplain ModItems} instead.
 */
@Deprecated
@SuppressWarnings({"unused"})
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
//@ObjectHolder(QForgeUtils.MOD_ID)
public class ItemInit extends ObjectInit<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeMod.MOD_ID);

    /**
     * Register item.
     *
     * @param name     the internal name for the item.
     * @param supplier item supplier for registration.
     * @param <T>      item.
     * @return the item registry object.
     */
    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> supplier) {
        return ITEMS.register(name, supplier);
    }

    /**
     * Register a spawn egg.
     *
     * @param entityTypeProvider the entity type provider for the spawn egg to use.
     * @param primaryColor       the primary color.
     * @param secondaryColor     the secondary color.
     * @param <ENTITY>           the entity.
     * @return the spawn egg.
     */
    private static <ENTITY extends Entity> ItemRegistryObject<CustomSpawnEggItem<ENTITY>> registerSpawnEgg(EntityTypeRegistryObject<ENTITY> entityTypeProvider, int primaryColor, int secondaryColor) {
        //Note: We are required to use a custom item as we cannot use the base SpawnEggItem due to the entity type not being initialized yet
        return ITEMS.register(entityTypeProvider.getName() + "_spawn_egg", () -> new CustomSpawnEggItem<>(entityTypeProvider, primaryColor, secondaryColor));
    }
}
