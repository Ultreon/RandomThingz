package com.qsoftware.forgemod.util.eventbus;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.client.gui.ExampleChestScreen;
import com.qsoftware.forgemod.client.renderer.*;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.init.types.ContainerTypesInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Screen factories.
//        ScreenManager.registerFactory(ContainerTypesInit.EXAMPLE_CHEST, ExampleChestScreen::new);

        // Entity renderers.
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HOG_ENTITY, HogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.WRAT_HOG_ENTITY, WratHogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BISON_ENTITY, BisonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.OX_ENTITY, OxRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.DUCK_ENTITY, DuckRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MOOBLOOM_ENTITY, MoobloomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_ENDERMAN_ENTITY, IceEndermanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FIRE_CREEPER_ENTITY, FireCreeperRenderer::new);
    }
}
