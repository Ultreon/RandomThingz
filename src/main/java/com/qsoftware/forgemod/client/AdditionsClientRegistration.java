package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.environment.ModEntities;
import com.qsoftware.forgemod.modules.environment.client.renderer.RenderBabyCreeper;
import com.qsoftware.forgemod.modules.environment.client.renderer.RenderBabyEnderman;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdditionsClientRegistration {
    private AdditionsClientRegistration() {
        throw ExceptionUtil.utilityConstructor();
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
//        new AdditionsKeyHandler();

        //Register entity rendering handlers
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_CREEPER, RenderBabyCreeper::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_ENDERMAN, RenderBabyEnderman::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_SKELETON, SkeletonRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_STRAY, StrayRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_WITHER_SKELETON, WitherSkeletonRenderer::new);
    }

}