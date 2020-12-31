package com.qsoftware.forgemod.registration.impl;

import com.qsoftware.forgemod.api.providers.IBlockProvider;
import com.qsoftware.forgemod.registration.WrappedRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@Deprecated
public class BlockRegistryObject<BLOCK extends Block> extends WrappedRegistryObject<BLOCK> implements IBlockProvider {

    public BlockRegistryObject(RegistryObject<BLOCK> registryObject) {
        super(registryObject);
    }

    @Nonnull
    @Override
    public BLOCK getBlock() {
        return get();
    }

    @NotNull
    @Override
    public Item getItem() {
        return get().asItem();
    }
}