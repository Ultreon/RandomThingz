package com.qsoftware.forgemod.modules.blocks;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreRegisterWrapperModule;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.modlib.silentlib.registry.BlockDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlocksModule extends CoreRegisterWrapperModule<Block> {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(QForgeMod.modId);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, QForgeMod.modId);

    @Override
    public void onEnable() {
        ModBlocks.register();
        ModBlocksNew.register();

        BLOCKS.register(modEventBus);
    }

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
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
