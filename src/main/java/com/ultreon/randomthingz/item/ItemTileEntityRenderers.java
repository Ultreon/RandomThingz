package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemTileEntityRenderers {
    private static final List<Supplier<BiFunction<BlockEntityRenderDispatcher, EntityModelSet, ?>>> laziness = new ArrayList<>();

    public static void lazyRegister(Supplier<BiFunction<BlockEntityRenderDispatcher, EntityModelSet, ?>> callableSupplier) {
        laziness.add(callableSupplier);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

    }
}
