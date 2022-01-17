package com.ultreon.randomthingz.item.group;

import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.common.BetterCreativeTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class BookshelvesTab extends BetterCreativeTab {
    public static final BookshelvesTab instance = new BookshelvesTab();

    public BookshelvesTab() {
        super(new ResourceLocation("bookshelves"), ModBlocks.BOOKSHELVES.get(0).get());
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(Blocks.BOOKSHELF);
    }
}
