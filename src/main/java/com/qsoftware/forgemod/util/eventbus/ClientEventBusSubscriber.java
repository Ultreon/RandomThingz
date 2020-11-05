package com.qsoftware.forgemod.util.eventbus;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.client.renderer.*;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.objects.entities.LegendaryEnderPearlEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HOG_ENTITY.get(), HogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.WRAT_HOG_ENTITY.get(), WratHogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BISON_ENTITY.get(), BisonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.OX_ENTITY.get(), OxRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.DUCK_ENTITY.get(), DuckRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MOOBLOOM_ENTITY.get(), MoobloomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_ENDERMAN_ENTITY.get(), IceEndermanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FIRE_CREEPER_ENTITY.get(), FireCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.GLOW_SQUID_ENTITY.get(), GlowSquidRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LEGENDARY_ENDER_PEARL.get(), manager -> new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
    }
}
