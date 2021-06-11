package com.qtech.randomthingz.modules.tiles;

import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.commons.CoreRegisterModule;
import com.qtech.randomthingz.commons.ModuleSafety;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntitiesModule extends CoreRegisterModule<TileEntityType<?>> {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = create(ForgeRegistries.TILE_ENTITIES);

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public void onEnable() {
        ModTileEntities.register();
        ModMachineTileEntities.register();

        TILE_ENTITIES.register(modEventBus);
    }

    @Override
    public @NotNull String getName() {
        return "tile_entities";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public DeferredRegister<TileEntityType<?>> getDeferredRegister() {
        return TILE_ENTITIES;
    }

    @Override
    public <O extends TileEntityType<?>> RegistryObject<O> register(String name, Supplier<O> supplier) {
        return TILE_ENTITIES.register(name, supplier);
    }
}
