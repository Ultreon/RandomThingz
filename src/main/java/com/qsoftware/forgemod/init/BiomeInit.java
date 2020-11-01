package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.world.biomes.ExampleBiome;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class BiomeInit {
    public static final Biome EXAMPLE_BIOME = new ExampleBiome().build();

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        Class<BlockInit> clazz = BlockInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Biome.class.isAssignableFrom(field.getType())) {
                try {
                    Biome item = (Biome) field.get(null);
                    event.getRegistry().register(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering item: " + field.getName(), t);
                }
            }
        }
    }
}
