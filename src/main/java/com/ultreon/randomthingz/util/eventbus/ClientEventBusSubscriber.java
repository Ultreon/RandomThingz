package com.ultreon.randomthingz.util.eventbus;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.entity.ModBlockEntities;
import com.ultreon.randomthingz.client.gui.screen.ExampleChestScreen;
import com.ultreon.randomthingz.client.model.CluckshroomModel;
import com.ultreon.randomthingz.client.model.MoobloomModel;
import com.ultreon.randomthingz.client.renderer.GlowSquidRenderer;
import com.ultreon.randomthingz.client.renderer.*;
import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.init.ModContainers;
import com.ultreon.randomthingz.block.entity.renderer.ChristmasChestBlockEntityRenderer;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client eventbus subscriber.
 * Currently, used for registering renderers for entities.
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
     * Client setup event for client side render registration.
     *
     * @param event the {@linkplain FMLClientSetupEvent} event.
     */
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MoobloomModel.LAYER_LOCATION, MoobloomModel::createBodyLayer);
        event.registerLayerDefinition(CluckshroomModel.LAYER_LOCATION, CluckshroomModel::createBodyLayer);

    }

    /**
     * Register screen factories.
     */
    private static void registerScreenFactories() {
        MenuScreens.register(ModContainers.WOODEN_CRATE.get(), ExampleChestScreen::new);
    }

    /**
     * Register entity renderers for baby variants of monsters.
     */
    private static void registerEntityBabyRenderers() {
        EntityRenderers.register(ModEntities.BABY_CREEPER.get(), BabyCreeperRenderer::new);
        EntityRenderers.register(ModEntities.BABY_ENDERMAN.get(), BabyEndermanRenderer::new);
        EntityRenderers.register(ModEntities.BABY_SKELETON.get(), SkeletonRenderer::new);
        EntityRenderers.register(ModEntities.BABY_STRAY.get(), StrayRenderer::new);
        EntityRenderers.register(ModEntities.BABY_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);
    }

    /**
     * Register entity renderers.
     */
    private static void registerEntityRenderers() {
        EntityRenderers.register(ModEntities.HOG.get(), HogRenderer::new);
        EntityRenderers.register(ModEntities.WARTHOG.get(), WarthogRenderer::new);
        EntityRenderers.register(ModEntities.BISON.get(), BisonRenderer::new);
        EntityRenderers.register(ModEntities.OX.get(), OxRenderer::new);
        EntityRenderers.register(ModEntities.DUCK.get(), DuckRenderer::new);
        EntityRenderers.register(ModEntities.CLUCKSHROOM.get(), CluckshroomRenderer::new);
        EntityRenderers.register(ModEntities.CUSTOM_TNT.get(), CustomTntRenderer::new);
        EntityRenderers.register(ModEntities.MOOBLOOM.get(), MoobloomRenderer::new);
        EntityRenderers.register(ModEntities.ICE_ENDERMAN.get(), IceEndermanRenderer::new);
        EntityRenderers.register(ModEntities.FIRE_CREEPER.get(), FireCreeperRenderer::new);
        EntityRenderers.register(ModEntities.GLOW_SQUID.get(), GlowSquidRenderer::new);
        EntityRenderers.register(ModEntities.LEGENDARY_ENDER_PEARL.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.DYNAMITE.get(), ThrownItemRenderer::new);
    }

    /**
     * Bind tile entity renderers.
     */
    private static void bindTileEntityRenderers() {
        BlockEntityRenderers.register(ModBlockEntities.CHRISTMAS_CHEST.get(), ChristmasChestBlockEntityRenderer::new);
    }
}
