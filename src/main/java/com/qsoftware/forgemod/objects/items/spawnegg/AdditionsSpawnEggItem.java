package com.qsoftware.forgemod.objects.items.spawnegg;

import com.qsoftware.forgemod.api.NBTConstants;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import com.qsoftware.forgemod.registration.impl.ItemDeferredRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Game PC block class.
 *
 * @author QForgeUtils community.
 * @deprecated Use {@link CustomSpawnEggItem} instead.
 */
@Deprecated
public class AdditionsSpawnEggItem extends SpawnEggItem {

    private final EntityTypeRegistryObject<?> entityTypeRO;

    @SuppressWarnings("ConstantConditions")
    public AdditionsSpawnEggItem(EntityTypeRegistryObject<?> entityTypeRO, int primaryColor, int secondaryColor) {
        //Note: We pass null for now so that it does not override "pick block" on skeletons or some other existing type
        super(null, primaryColor, secondaryColor, ItemDeferredRegister.getMekBaseProperties().group(Groups.SPAWN_EGGS));
        this.entityTypeRO = entityTypeRO;
    }

    @Nonnull
    @Override
    public EntityType<?> getType(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains(NBTConstants.ENTITY_TAG, NBT.TAG_COMPOUND)) {
            CompoundNBT entityTag = nbt.getCompound(NBTConstants.ENTITY_TAG);
            if (entityTag.contains(NBTConstants.ID, NBT.TAG_STRING)) {
                return EntityType.byKey(entityTag.getString(NBTConstants.ID)).orElse(entityTypeRO.getEntityType());
            }
        }
        return entityTypeRO.getEntityType();
    }
}