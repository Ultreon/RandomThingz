package com.ultreon.randomthingz.item;

import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.IItemProvider;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public enum CraftingItems implements IItemProvider {
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

    private final Food food;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> item;

    CraftingItems() {
        this(null);
    }

    CraftingItems(@Nullable Food food) {
        this.food = food;
    }

    public static void register() {
        for (CraftingItems item : values()) {
            item.item = Registration.ITEMS.register(item.getName(), () -> new Item(createProperties(item.food)));
        }
    }

    private static Item.Properties createProperties(@Nullable Food food) {
        if (food != null) {
            return new Item.Properties().group(ItemGroup.FOOD).food(food);
        }
        return new Item.Properties().group(ItemGroup.MISC);
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
