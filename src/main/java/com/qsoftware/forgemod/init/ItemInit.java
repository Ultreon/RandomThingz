package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.objects.items.*;
import com.qsoftware.forgemod.objects.items.advanced.AdvancedBowItem;
import com.qsoftware.forgemod.objects.items.base.IngotOrDustItem;
import com.qsoftware.forgemod.objects.items.base.IngredientItem;
import com.qsoftware.forgemod.objects.items.base.KnifeItem;
import com.qsoftware.forgemod.objects.items.base.SliceableItem;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import com.qsoftware.forgemod.registration.impl.ItemDeferredRegister;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import com.qsoftware.forgemod.util.builder.ArmorMaterialBuilder;
import com.qsoftware.forgemod.util.builder.ItemTierBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

@Deprecated
@SuppressWarnings({"unused", "NumericOverflow"})
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
//@ObjectHolder(QForgeUtils.MOD_ID)
public class ItemInit extends ObjectInit<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeMod.MOD_ID);

    /**
     * Register item.
     *
     * @param name the internal name for the item.
     * @param supplier item supplier for registration.
     * @param <T> item.
     * @return the item registry object.
     */
    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> supplier) {
        return ITEMS.register(name, supplier);
    }

    /**
     * Register a spawn egg.
     *
     * @param entityTypeProvider the entity type provider for the spawn egg to use.
     * @param primaryColor the primary color.
     * @param secondaryColor the secondary color.
     * @param <ENTITY> the entity.
     * @return the spawn egg.
     */
    private static <ENTITY extends Entity> ItemRegistryObject<CustomSpawnEggItem<ENTITY>> registerSpawnEgg(EntityTypeRegistryObject<ENTITY> entityTypeProvider, int primaryColor, int secondaryColor) {
        //Note: We are required to use a custom item as we cannot use the base SpawnEggItem due to the entity type not being initialized yet
        return ITEMS.register(entityTypeProvider.getInternalRegistryName() + "_spawn_egg", () -> new CustomSpawnEggItem<>(entityTypeProvider, primaryColor, secondaryColor));
    }
}
