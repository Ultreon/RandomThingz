package com.ultreon.randomthingz.block._common;

import com.qsoftware.modlib.silentlib.registry.BlockDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.common.CoreRegisterWrapperModule;
import com.ultreon.randomthingz.common.ModuleSafety;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
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
