package com.qtech.randomthingz.block.common;

import com.qsoftware.modlib.silentlib.registry.BlockDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.commons.CoreRegisterWrapperModule;
import com.qtech.randomthingz.commons.ModuleSafety;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlocksModule extends CoreRegisterWrapperModule<Block> {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(RandomThingz.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, RandomThingz.MOD_ID);

    @Override
    public void onEnable() {
        ModBlocks.register();
        ModBlocksAlt.register();

        BLOCKS.register(modEventBus);
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public String getName() {
        return "blocks";
    }

    @Override
    public ModuleCompatibility getCompatibility() {
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
