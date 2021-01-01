package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.common.BetterItemGroup;
import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class BookshelfsItemGroup extends BetterItemGroup {
    public static final BookshelfsItemGroup instance = new BookshelfsItemGroup();

    public BookshelfsItemGroup() {
        super(new ResourceLocation("bookshelfs"), ModBlocks.BOOKSHELFS.get(0).get());
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.BOOKSHELF);
    }

    @Override
    public void fill(@NotNull NonNullList<ItemStack> items) {
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
