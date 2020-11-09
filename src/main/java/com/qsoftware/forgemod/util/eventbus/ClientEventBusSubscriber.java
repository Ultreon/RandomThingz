package com.qsoftware.forgemod.util.eventbus;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.client.renderer.*;
import com.qsoftware.forgemod.client.renderer.entity.RenderBabyCreeper;
import com.qsoftware.forgemod.client.renderer.entity.RenderBabyEnderman;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client eventbus subscriber.
 * Currently used for registering renderers for entities.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Screen factories.
//        ScreenManager.registerFactory(ContainerTypesInit.EXAMPLE_CHEST, ExampleChestScreen::new);

        // Entity renderers.

        // Baby variants
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BABY_CREEPER.get(), RenderBabyCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BABY_ENDERMAN.get(), RenderBabyEnderman::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BABY_SKELETON.get(), SkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BABY_STRAY.get(), StrayRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BABY_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);

        // Entities
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.HOG.get(), HogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.WARTHOG.get(), WarthogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.BISON.get(), BisonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.OX.get(), OxRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.DUCK.get(), DuckRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.MOOBLOOM.get(), MoobloomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ICE_ENDERMAN.get(), IceEndermanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FIRE_CREEPER.get(), FireCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.GLOW_SQUID.get(), GlowSquidRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.LEGENDARY_ENDER_PEARL.get(), manager -> new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.DYNAMITE.get(), manager -> new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
    }
}
