package com.ultreon.randomthingz.world.biome.common;

import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.world.biome.objects.ExampleBiome;
import com.ultreon.randomthingz.world.biome.objects.GrasslandBiome;
import com.ultreon.randomthingz.world.gen.WorldGenerationModule;
import lombok.experimental.UtilityClass;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

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
