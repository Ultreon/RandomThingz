package com.ultreon.randomthingz.item;

import com.ultreon.modlib.silentlib.registry.EntityTypeRegistryObject;
import com.ultreon.modlib.silentlib.registry.ItemDeferredRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;

/**
 * Custom spawn egg item class.
 *
 * @author Qboi123
 */
public class CustomSpawnEggItem<T extends Mob> extends ForgeSpawnEggItem {
    private final EntityTypeRegistryObject<T> entityTypeIn;

    public CustomSpawnEggItem(EntityTypeRegistryObject<T> entityType, int primaryColor, int secondaryColor) {
        //Note: We pass null for now so that it does not override "pick block" on skeletons or some other existing type
        super(entityType::getEntityType, primaryColor, secondaryColor, ItemDeferredRegister.getMekBaseProperties().tab(CreativeModeTab.TAB_MISC));
        this.entityTypeIn = entityType;
    }

    public @NotNull EntityType<T> getEntityType() {
        return entityTypeIn.get();
    }
}
