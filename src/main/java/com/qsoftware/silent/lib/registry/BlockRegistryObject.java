package com.qsoftware.silent.lib.registry;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import com.qsoftware.silent.lib.block.IBlockProvider;

public class BlockRegistryObject<T extends Block> extends RegistryObjectWrapper<T> implements IBlockProvider {
    public BlockRegistryObject(RegistryObject<T> block) {
        super(block);
    }

    @Override
    public Block asBlock() {
        return registryObject.get();
    }
}
