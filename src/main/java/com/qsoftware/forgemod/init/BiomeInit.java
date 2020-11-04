package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.world.biomes.ExampleBiome;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class BiomeInit extends ObjectInit<Biome> {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, QForgeUtils.MOD_ID);
    public static final RegistryObject<Biome> EXAMPLE_BIOME = register("example_biome", () -> new ExampleBiome().build());

    private static <T extends Biome> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return BIOMES.register(name, supplier);
    }
}
