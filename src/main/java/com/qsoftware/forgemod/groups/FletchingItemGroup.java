package com.qsoftware.forgemod.groups;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Bows & arrows item group..
 *
 * @author Qboi123
 */
public class FletchingItemGroup extends ItemGroup {
    public static final FletchingItemGroup instance = new FletchingItemGroup(ItemGroup.GROUPS.length, "qforgemod_fletching");

    public FletchingItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.BOW);
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        ItemStack stack = new ItemStack(Items.BOW);
        stack.addEnchantment(Enchantments.FLAME, 1);
        stack.addEnchantment(Enchantments.POWER, 5);
        stack.addEnchantment(Enchantments.PUNCH, 5);
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putByte("Unbreakable", (byte) 1);

        items.add(new ItemStack(Items.BOW));
        items.add(new ItemStack(Items.ARROW));
        items.add(new ItemStack(Items.SPECTRAL_ARROW));
        items.add(stack);
    }
}
