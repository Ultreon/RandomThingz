package com.ultreon.randomthingz.item.group;

import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.common.BetterItemGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class BookshelvesItemGroup extends BetterItemGroup {
    public static final BookshelvesItemGroup instance = new BookshelvesItemGroup();

    public BookshelvesItemGroup() {
        super(new ResourceLocation("bookshelfs"), ModBlocks.BOOKSHELVES.get(0).get());
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(Blocks.BOOKSHELF);
    }
}
