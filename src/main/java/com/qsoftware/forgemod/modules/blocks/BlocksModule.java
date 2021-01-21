package com.qsoftware.forgemod.modules.blocks;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreRegisterWrapperModule;
import com.qsoftware.modlib.silentlib.registry.BlockDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BlocksModule extends CoreRegisterWrapperModule<Block> {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(QForgeMod.MOD_ID);

    @Override
    public void onEnable() {
        ModBlocks.register();
        ModBlocksNew.register();

        BLOCKS.register(modEventBus);
    }

    @Override
    public @NotNull String getName() {
        return "blocks";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public BlockDeferredRegister getDeferredRegister() {
        return BLOCKS;
    }

    @Override
    public <O extends Block> BlockRegistryObject<O> register(String name, Supplier<O> supplier) {
        return BLOCKS.register(name, supplier);
    }
}
