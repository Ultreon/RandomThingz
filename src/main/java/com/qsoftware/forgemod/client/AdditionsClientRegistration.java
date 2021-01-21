package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.entities.model.AdditionsModelCache;
import com.qsoftware.forgemod.modules.entities.renderer.RenderBabyCreeper;
import com.qsoftware.forgemod.modules.entities.renderer.RenderBabyEnderman;
import com.qsoftware.forgemod.common.interfaces.IHasDyeColor;
import com.qsoftware.forgemod.common.interfaces.IHasMaterialColor;
import com.qsoftware.forgemod.modules.items.ModItems;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.modules.items.ModItemsNew;
import com.qsoftware.forgemod.modules.entities.ModEntities;
import com.qsoftware.forgemod.modules.items.objects.base.DyeColorizedItem;
import com.qsoftware.forgemod.modules.items.objects.base.MaterialColorizedItem;
import com.qsoftware.forgemod.modules.items.objects.spawnegg.CustomSpawnEggItem;
import com.qsoftware.forgemod.util.ExceptionUtil;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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