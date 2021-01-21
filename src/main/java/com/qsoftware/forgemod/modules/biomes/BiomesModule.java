package com.qsoftware.forgemod.modules.biomes;

import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreRegisterModule;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BiomesModule extends CoreRegisterModule<Biome> {
    public static final DeferredRegister<Biome> BIOMES = create(ForgeRegistries.BIOMES);

    @Override
    public void onEnable() {
        ModBiomes.register();

        BIOMES.register(modEventBus);
    }

    @Override
    public @NotNull String getName() {
        return "biomes";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public DeferredRegister<Biome> getDeferredRegister() {
        return BIOMES;
    }

    @Override
    public <O extends Biome> RegistryObject<O> register(String name, Supplier<O> supplier) {
        return BIOMES.register(name, supplier);
    }
}
