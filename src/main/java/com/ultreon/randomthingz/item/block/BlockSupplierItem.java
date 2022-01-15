package com.ultreon.randomthingz.item.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
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
    public @NotNull String getDescriptionId() {
        return this.block.getDescriptionId();
    }

    @Override
    public @NotNull Block getBlock() {
        return this.supplier.get();
    }
}
