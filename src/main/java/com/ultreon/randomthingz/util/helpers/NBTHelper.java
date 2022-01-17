package com.ultreon.randomthingz.util.helpers;

import com.ultreon.randomthingz.block.machines.quarry.QuarryBlockEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * NBT helper.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@UtilityClass
public class NBTHelper {
    public static CompoundTag toNBT(Object o) {
        if (o instanceof ItemStack) {
            return writeItemStack((ItemStack) o);
        }

        if (o instanceof QuarryBlockEntity) {
            return writeQuarry((QuarryBlockEntity) o);
        }

        return null;
    }

    private static CompoundTag writeQuarry(QuarryBlockEntity o) {
        CompoundTag compound = new CompoundTag();
        return o.save(compound);
    }

    @SuppressWarnings("ConstantConditions")
    private static CompoundTag writeItemStack(ItemStack i) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        nbt.putByte("type", (byte) 0);
        return nbt;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Nullable
    public static Object fromNBT(@NotNull CompoundTag compound) {
        switch (compound.getByte("type")) {
            case 0:
                return readItemStack(compound);
            default:
                return null;
        }
    }

    private static ItemStack readItemStack(CompoundTag compound) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
        int count = compound.getInt("count");
        return new ItemStack(item, count);
    }
}
