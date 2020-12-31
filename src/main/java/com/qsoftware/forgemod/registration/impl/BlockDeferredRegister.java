package com.qsoftware.forgemod.registration.impl;

import com.qsoftware.forgemod.api.providers.IBlockProvider;
import com.qsoftware.forgemod.registration.DeferredRegisterWrapper;
import com.qsoftware.silent.lib.registry.BlockRegistryObject;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Deprecated
public class BlockDeferredRegister extends DeferredRegisterWrapper<Block> {

    private final List<IBlockProvider> allBlocks = new ArrayList<>();

    public BlockDeferredRegister(String modid) {
        super(modid, ForgeRegistries.BLOCKS);
    }

    public <BLOCK extends Block> com.qsoftware.silent.lib.registry.BlockRegistryObject<BLOCK> register(String name, Supplier<? extends BLOCK> sup) {
        BlockRegistryObject<BLOCK> registeredBlock = register(name, sup, com.qsoftware.silent.lib.registry.BlockRegistryObject::new);
        allBlocks.add((IBlockProvider) registeredBlock);
        return registeredBlock;
    }

    public List<IBlockProvider> getAllBlocks() {
        return allBlocks;
    }
}