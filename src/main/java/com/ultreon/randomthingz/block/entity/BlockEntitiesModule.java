package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.common.CoreRegisterModule;
import com.ultreon.randomthingz.common.ModuleSafety;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockEntitiesModule extends CoreRegisterModule<BlockEntityType<?>> {
    public static final DeferredRegister<BlockEntityType<?>> BLOCE_ENTITIES = create(ForgeRegistries.BLOCK_ENTITIES);

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public void onEnable() {
        ModBlockEntities.register();
        ModMachines.register();

        BLOCE_ENTITIES.register(modEventBus);
    }

    @Override
    public @NotNull
    String getName() {
        return "tile_entities";
    }

    @Override
    public @NotNull
    ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public DeferredRegister<BlockEntityType<?>> getDeferredRegister() {
        return BLOCE_ENTITIES;
    }

    @Override
    public <O extends BlockEntityType<?>> RegistryObject<O> register(String name, Supplier<O> supplier) {
        return BLOCE_ENTITIES.register(name, supplier);
    }
}
