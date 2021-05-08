package com.qtech.randomthingz.item.blockitem;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Block supplier item class.
 *
 * @author MrCrayfish
 */
public class BlockSupplierItem extends BlockItem {
    private final Block block;
    private final Supplier<Block> supplier;

    public BlockSupplierItem(Properties properties, Block block, Supplier<Block> supplier) {
        super(block, properties);
        this.block = block;
        this.supplier = supplier;
    }

    @Override
    public @NotNull String getTranslationId() {
        return this.block.getTranslationId();
    }

    @Override
    public @NotNull Block getBlock() {
        return this.supplier.get();
    }
}
