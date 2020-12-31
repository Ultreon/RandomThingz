package com.qsoftware.silent.lib.registry;

import com.qsoftware.forgemod.api.providers.IBlockProvider;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class BlockRegistryObject<T extends Block> extends RegistryObjectWrapper<T> implements IBlockProvider {
    public BlockRegistryObject(RegistryObject<T> block) {
        super(block);
    }

    @NotNull
    @Override
    public Block getBlock() {
        return asBlock();
    }

    @Override
    public Block asBlock() {
        return registryObject.get();
    }
}
