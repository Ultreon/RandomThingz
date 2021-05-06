package com.qtech.forgemod.world.biome.common;

import com.qtech.forgemod.init.ObjectInit;
import com.qtech.forgemod.world.biome.objects.GrasslandBiome;
import com.qtech.forgemod.world.biome.objects.ExampleBiome;
import com.qtech.forgemod.world.gen.WorldGenerationModule;
import lombok.experimental.UtilityClass;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Biomes initialization class.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@UtilityClass
public class ModBiomes extends ObjectInit<Biome> {
    public static final DeferredRegister<Biome> BIOMES = WorldGenerationModule.BIOMES;

    public static final RegistryObject<Biome> EXAMPLE_BIOME = register("example_biome", () -> new ExampleBiome().build());
    public static final RegistryObject<Biome> GRASSLAND = register("grassland", () -> new GrasslandBiome().build());

    private static <T extends Biome> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return BIOMES.register(name, supplier);
    }

    public static void register() {

    }
}
