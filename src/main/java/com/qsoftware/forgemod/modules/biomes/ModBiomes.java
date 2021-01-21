package com.qsoftware.forgemod.modules.biomes;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.util.ExceptionUtil;
import com.qsoftware.forgemod.modules.biomes.objects.ExampleBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Biomes initialization class.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
public class ModBiomes extends ObjectInit<Biome> {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, QForgeMod.MOD_ID);
    public static final RegistryObject<Biome> EXAMPLE_BIOME = register("example_biome", () -> new ExampleBiome().build());

    private ModBiomes() {
        throw ExceptionUtil.utilityConstructor();
    }

    private static <T extends Biome> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return BIOMES.register(name, supplier);
    }

    public static void register() {

    }
}
