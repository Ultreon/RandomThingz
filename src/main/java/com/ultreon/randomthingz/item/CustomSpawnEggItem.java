package com.ultreon.randomthingz.item;

import com.ultreon.modlib.api.NBTConstants;
import com.ultreon.modlib.embedded.silentlib.registry.EntityTypeRegistryObject;
import com.ultreon.modlib.embedded.silentlib.registry.ItemDeferredRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Custom spawn egg item class.
 *
 * @author Qboi123
 */
public class CustomSpawnEggItem<T extends Entity> extends SpawnEggItem {
    private final EntityTypeRegistryObject<T> entityTypeIn;

    @SuppressWarnings("ConstantConditions")
    public CustomSpawnEggItem(EntityTypeRegistryObject<T> entityTypeIn, int primaryColor, int secondaryColor) {
        //Note: We pass null for now so that it does not override "pick block" on skeletons or some other existing type
        super(null, primaryColor, secondaryColor, ItemDeferredRegister.getMekBaseProperties().tab(CreativeModeTab.TAB_MISC));
        this.entityTypeIn = entityTypeIn;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            int index = -1;
            int i = 0;
            for (ItemStack stack : items) {
                if (stack.getItem() instanceof SpawnEggItem) {
                    index = i;
                }
                i++;
            }
            if (index < 0) {
                items.add(new ItemStack(this));
            } else {
                items.add(index + 1, new ItemStack(this));
            }
        }
    }

    @Override
    public @NotNull EntityType<? extends Entity> getType(@Nullable CompoundTag nbt) {
        if (nbt != null && nbt.contains(NBTConstants.ENTITY_TAG, Constants.NBT.TAG_COMPOUND)) {
            CompoundTag entityTag = nbt.getCompound(NBTConstants.ENTITY_TAG);
            if (entityTag.contains(NBTConstants.ID, Constants.NBT.TAG_STRING)) {
                return EntityType.byString(entityTag.getString(NBTConstants.ID)).orElse(entityTypeIn.get());
            }
        }
        return entityTypeIn.get();
    }

    public @NotNull EntityType<T> getEntityType() {
        return entityTypeIn.get();
    }
}
