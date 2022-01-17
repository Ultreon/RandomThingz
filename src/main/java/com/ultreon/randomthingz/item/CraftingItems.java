package com.ultreon.randomthingz.item;

import com.ultreon.modlib.embedded.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public enum CraftingItems implements ItemLike {
    // Other dusts
    COAL_DUST,
    // Crafting items
    CIRCUIT_BOARD,
    HEATING_ELEMENT,
    SOLDER,
    PLASTIC_PELLETS,
    PLASTIC_SHEET,
    UPGRADE_CASE,
    ZOMBIE_LEATHER,
    // Others
    BEEF_JERKY(Foods.COOKED_BEEF),
    PORK_JERKY(Foods.COOKED_PORKCHOP),
    CHICKEN_JERKY(Foods.COOKED_CHICKEN),
    MUTTON_JERKY(Foods.COOKED_MUTTON),
    RABBIT_JERKY(Foods.COOKED_RABBIT),
    COD_JERKY(Foods.COOKED_COD),
    SALMON_JERKY(Foods.SALMON),
    ;

    private final FoodProperties food;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> item;

    CraftingItems() {
        this(null);
    }

    CraftingItems(@Nullable FoodProperties food) {
        this.food = food;
    }

    public static void register() {
        for (CraftingItems item : values()) {
            item.item = Registration.ITEMS.register(item.getName(), () -> new Item(createProperties(item.food)));
        }
    }

    private static Item.Properties createProperties(@Nullable FoodProperties food) {
        if (food != null) {
            return new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(food);
        }
        return new Item.Properties().tab(CreativeModeTab.TAB_MISC);
    }

    public String getName() {
//        return TextUtils.lower(name());
        return StringUtils.lowerCase(name());
    }

    @Override
    public Item asItem() {
        return item.get();
    }
}
