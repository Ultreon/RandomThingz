package com.qtech.randomthingz.util.eventbus;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.renderer.*;
import com.qtech.randomthingz.entity.common.ModEntities;
import com.qtech.randomthingz.modules.tiles.ModTileEntities;
import com.qtech.randomthingz.modules.ui.ModContainers;
import com.qtech.randomthingz.modules.ui.screens.ExampleChestScreen;
import com.qtech.randomthingz.tileentity.renderer.ChristmasChestTileEntityRenderer;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client eventbus subscriber.
 * Currently used for registering renderers for entities.
 *
 * @author Qboi123
 */
@UtilityClass
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    /**
     * Client setup event for client side render registration.
     *
     * @param event the {@linkplain FMLClientSetupEvent} event.
     */
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ClientEventBusSubscriber.registerScreenFactories();
        ClientEventBusSubscriber.bindTileEntityRenderers();
        ClientEventBusSubscriber.registerEntityRenderers();
        ClientEventBusSubscriber.registerEntityBabyRenderers();

    }

    /**
     * Register screen factories.
     */
    private static void registerScreenFactories() {
        ScreenManager.registerFactory(ModContainers.WOODEN_CRATE.get(), ExampleChestScreen::new);
    }

    /**
     * Register entity renderers for baby variants of monsters.
     */
    private static void registerEntityBabyRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BABY_CREEPER.get(), RenderBabyCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BABY_ENDERMAN.get(), RenderBabyEnderman::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BABY_SKELETON.get(), SkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BABY_STRAY.get(), StrayRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BABY_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);
    }

    /**
     * Register entity renderers.
     */
    private static void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOG.get(), HogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WARTHOG.get(), WarthogRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BISON.get(), BisonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.OX.get(), OxRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.DUCK.get(), DuckRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CLUCKSHROOM.get(), CluckshroomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CUSTOM_TNT.get(), CustomTNTRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOOBLOOM.get(), MoobloomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ICE_ENDERMAN.get(), IceEndermanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FIRE_CREEPER.get(), FireCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GLOW_SQUID.get(), GlowSquidRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LEGENDARY_ENDER_PEARL.get(), manager -> new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.DYNAMITE.get(), manager -> new SpriteRenderer<>(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()));
    }

    /**
     * Bind tile entity renderers.
     */
    private static void bindTileEntityRenderers() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.CHRISTMAS_CHEST.get(), ChristmasChestTileEntityRenderer::new);
    }
}
