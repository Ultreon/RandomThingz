package com.qsoftware.forgemod.modules.items.objects.spawnegg;

import com.qsoftware.modlib.api.NBTConstants;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.modlib.silentlib.registry.EntityTypeRegistryObject;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
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
        super(null, primaryColor, secondaryColor, ItemDeferredRegister.getMekBaseProperties().group(ModItemGroups.SPAWN_EGGS));
        this.entityTypeIn = entityTypeIn;
    }

    @Override
    public @NotNull EntityType<? extends Entity> getType(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains(NBTConstants.ENTITY_TAG, Constants.NBT.TAG_COMPOUND)) {
            CompoundNBT entityTag = nbt.getCompound(NBTConstants.ENTITY_TAG);
            if (entityTag.contains(NBTConstants.ID, Constants.NBT.TAG_STRING)) {
                return EntityType.byKey(entityTag.getString(NBTConstants.ID)).orElse(entityTypeIn.get());
            }
        }
        return entityTypeIn.get();
    }

    public @NotNull EntityType<T> getEntityType() {
        return entityTypeIn.get();
    }
}
