package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.entity.ModBlockEntities;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.block.entity.renderer.ChristmasChestBlockEntityRenderer;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlockEntityRenderer;
import com.ultreon.randomthingz.client.model.BabyCreeperModel;
import com.ultreon.randomthingz.client.model.BabyEndermanModel;
import com.ultreon.randomthingz.client.model.CluckshroomModel;
import com.ultreon.randomthingz.client.model.MoobloomModel;
import com.ultreon.randomthingz.client.renderer.*;
import com.ultreon.randomthingz.common.entity.ModEntities;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BABY_CREEPER.get(), BabyCreeperRenderer::new);
        event.registerEntityRenderer(ModEntities.BABY_ENDERMAN.get(), BabyEndermanRenderer::new);
        event.registerEntityRenderer(ModEntities.BABY_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(ModEntities.BABY_STRAY.get(), StrayRenderer::new);
        event.registerEntityRenderer(ModEntities.BABY_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);
        event.registerEntityRenderer(ModEntities.BISON.get(), BisonRenderer::new);
        event.registerEntityRenderer(ModEntities.CLUCKSHROOM.get(), CluckshroomRenderer::new);
        event.registerEntityRenderer(ModEntities.CUSTOM_TNT.get(), CustomTntRenderer::new);
        event.registerEntityRenderer(ModEntities.DUCK.get(), DuckRenderer::new);
        event.registerEntityRenderer(ModEntities.DYNAMITE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.FIRE_CREEPER.get(), FireCreeperRenderer::new);
        event.registerEntityRenderer(ModEntities.GLOW_SQUID.get(), GlowSquidRenderer::new);
        event.registerEntityRenderer(ModEntities.HOG.get(), HogRenderer::new);
        event.registerEntityRenderer(ModEntities.ICE_ENDERMAN.get(), IceEndermanRenderer::new);
        event.registerEntityRenderer(ModEntities.LEGENDARY_ENDER_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.MOOBLOOM.get(), MoobloomRenderer::new);
        event.registerEntityRenderer(ModEntities.OX.get(), OxRenderer::new);
        event.registerEntityRenderer(ModEntities.WARTHOG.get(), WarthogRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.CHRISTMAS_CHEST.get(), ChristmasChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModMachines.DRYING_RACK.get(), DryingRackBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BabyCreeperModel.LAYER_LOCATION, BabyCreeperModel::createBodyLayer);
        event.registerLayerDefinition(BabyEndermanModel.LAYER_LOCATION, BabyEndermanModel::createBodyLayer);
        event.registerLayerDefinition(CluckshroomModel.LAYER_LOCATION, CluckshroomModel::createBodyLayer);
        event.registerLayerDefinition(MoobloomModel.LAYER_LOCATION, MoobloomModel::createBodyLayer);
    }
}
