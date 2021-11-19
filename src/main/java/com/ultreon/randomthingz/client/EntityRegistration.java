package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.RenderBabyCreeper;
import com.ultreon.randomthingz.client.renderer.RenderBabyEnderman;
import com.ultreon.randomthingz.entity.common.ModEntities;
import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@UtilityClass
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegistration {
    @SubscribeEvent
    public static void initialize(FMLClientSetupEvent event) {
        //Register entity rendering handlers
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_CREEPER, RenderBabyCreeper::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_ENDERMAN, RenderBabyEnderman::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_SKELETON, SkeletonRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_STRAY, StrayRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_WITHER_SKELETON, WitherSkeletonRenderer::new);
    }
}
