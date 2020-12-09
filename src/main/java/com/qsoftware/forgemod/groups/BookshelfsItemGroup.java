package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.forgemod.init.ModItems;
import com.qsoftware.silent.lib.registry.BlockRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class BookshelfsItemGroup extends ItemGroup {
    public static final BookshelfsItemGroup instance = new BookshelfsItemGroup(ItemGroup.GROUPS.length, "qforgemod_bookshelfs");

    public BookshelfsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.BOOKSHELF);
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
//        super.fill(items);
        ArrayList<BlockRegistryObject<Block>> bookshelfs = ModBlocks.BOOKSHELFS;

//        for (int i = 0; i < 4; i++) {
//            items.add(new ItemStack(Blocks.AIR));
//        }
//
//        items.add(new ItemStack(bookshelfs.get(0).get()));
//
//        for (int i = 0; i < 4; i++) {
//            items.add(new ItemStack(Blocks.AIR));
//        }

        for (int j = 1; j < 121; j += 15) {
            for (int i = j; i <= j + 7; i++) {
                if (i >= 121) {
                    continue;
                }
                items.add(new ItemStack(bookshelfs.get(i).get()));
            }

            for (int i = j + 8; i <= j + 14; i++) {
                if (i >= 121) {
                    continue;
                }
                items.add(new ItemStack(bookshelfs.get(i).get()));
            }

            items.add(new ItemStack(Blocks.AIR));
            items.add(new ItemStack(Blocks.AIR));
            items.add(new ItemStack(Blocks.AIR));
        }

        for (int j = 121; j < 135; j += 15) {
            items.add(new ItemStack(bookshelfs.get(0).get()));
            for (int i = j; i <= j + 6; i++) {
                if (i >= 135) {
                    continue;
                }
                items.add(new ItemStack(bookshelfs.get(i).get()));
            }

            for (int i = j + 7; i <= j + 14; i++) {
                if (i >= 135) {
                    continue;
                }
                items.add(new ItemStack(bookshelfs.get(i).get()));
            }

            items.add(new ItemStack(Blocks.AIR));
            items.add(new ItemStack(Blocks.AIR));
            items.add(new ItemStack(Blocks.AIR));
        }

        for (int j = 135; j < 225; j += 15) {
            for (int i = j; i <= j + 7; i++) {
                if (i >= 225) {
                    return;
                }
                items.add(new ItemStack(bookshelfs.get(i).get()));
            }

            for (int i = j + 8; i <= j + 14; i++) {
                if (i >= 225) {
                    return;
                }
                items.add(new ItemStack(bookshelfs.get(i).get()));
            }

            items.add(new ItemStack(Blocks.AIR));
            items.add(new ItemStack(Blocks.AIR));
            items.add(new ItemStack(Blocks.AIR));
        }
    }
}
