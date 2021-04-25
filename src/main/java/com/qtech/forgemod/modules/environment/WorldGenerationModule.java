package com.qtech.forgemod.modules.environment;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.client.gui.modules.ModuleCompatibility;
import com.qtech.forgemod.common.CoreRegisterModule;
import com.qtech.forgemod.common.ModuleSecurity;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;

public class WorldGenerationModule extends CoreRegisterModule<Biome> {
    public static final DeferredRegister<Biome> BIOMES = create(ForgeRegistries.BIOMES);

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    @Override
    public void onEnable() {
        ModBiomes.register();
        BIOMES.register(modEventBus);

        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onBiomeLoading(BiomeLoadingEvent event) {
        ModGeneration.loadTrees(event);
        ModGeneration.loadLakes(event);
    }

    public static Collection<RegistryObject<Biome>> getBiomes() {
        return BIOMES.getEntries();
    }

    @Override
    public @NotNull String getName() {
        return "world_gen";
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
