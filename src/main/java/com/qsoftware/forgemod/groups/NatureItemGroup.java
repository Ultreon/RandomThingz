package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Nature item groups.
 * Such as saplings, flowers.
 *
 * @author Qboi123
 */
public class NatureItemGroup extends ItemGroup {
    public static final NatureItemGroup instance = new NatureItemGroup(ItemGroup.GROUPS.length, "qforgemod_nature");

    public NatureItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModItems.EUCALYPTUS_LEAF.get());
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.add(new ItemStack(Items.DANDELION));
        items.add(new ItemStack(Items.POPPY));
        items.add(new ItemStack(Items.BLUE_ORCHID));
        items.add(new ItemStack(Items.ALLIUM));
        items.add(new ItemStack(Items.AZURE_BLUET));
        items.add(new ItemStack(Items.RED_TULIP));
        items.add(new ItemStack(Items.ORANGE_TULIP));
        items.add(new ItemStack(Items.WHITE_TULIP));
        items.add(new ItemStack(Items.PINK_TULIP));
        items.add(new ItemStack(Items.OXEYE_DAISY));
        items.add(new ItemStack(Items.CORNFLOWER));
        items.add(new ItemStack(Items.LILY_OF_THE_VALLEY));
        items.add(new ItemStack(ModBlocks.BUTTERCUP));
        items.add(new ItemStack(Items.WITHER_ROSE));
        items.add(new ItemStack(Items.OAK_SAPLING));
        items.add(new ItemStack(Items.BIRCH_SAPLING));
        items.add(new ItemStack(Items.SPRUCE_SAPLING));
        items.add(new ItemStack(Items.JUNGLE_SAPLING));
        items.add(new ItemStack(Items.ACACIA_SAPLING));
        items.add(new ItemStack(Items.DARK_OAK_SAPLING));
        items.add(new ItemStack(ModBlocks.EUCALYPTUS_SAPLING));
        items.add(new ItemStack(ModBlocks.CHERRY_SAPLING));
        items.add(new ItemStack(Items.SUNFLOWER));
        items.add(new ItemStack(Items.LILAC));
        items.add(new ItemStack(Items.ROSE_BUSH));
        items.add(new ItemStack(Items.PEONY));
        items.add(new ItemStack(Items.DEAD_BUSH));
        items.add(new ItemStack(Items.VINE));
        items.add(new ItemStack(Items.GRASS));
        items.add(new ItemStack(Items.FERN));
        items.add(new ItemStack(Items.SUGAR_CANE));
        items.add(new ItemStack(Items.BAMBOO));
        items.add(new ItemStack(Items.CACTUS));
        items.add(new ItemStack(Items.TALL_GRASS));
        items.add(new ItemStack(Items.LARGE_FERN));
        items.add(new ItemStack(Items.LILY_PAD));
        items.add(new ItemStack(Items.PODZOL));
        items.add(new ItemStack(Items.GRASS_BLOCK));
        items.add(new ItemStack(Items.OAK_LEAVES));
        items.add(new ItemStack(Items.BIRCH_LEAVES));
        items.add(new ItemStack(Items.SPRUCE_LEAVES));
        items.add(new ItemStack(Items.JUNGLE_LEAVES));
        items.add(new ItemStack(Items.ACACIA_LEAVES));
        items.add(new ItemStack(Items.DARK_OAK_LEAVES));
        items.add(new ItemStack(ModBlocks.EUCALYPTUS_LEAVES));
        items.add(new ItemStack(ModBlocks.CHERRY_LEAVES));
    }
}
