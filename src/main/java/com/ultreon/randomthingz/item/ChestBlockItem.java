package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.item.block.DeprecatedBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;

import java.util.function.Supplier;

public class ChestBlockItem extends DeprecatedBlockItem {
    public <T extends Block> ChestBlockItem(ChestBlock supplier, Properties properties) {
        super(supplier, properties);
    }
}
