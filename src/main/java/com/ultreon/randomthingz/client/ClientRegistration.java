package com.ultreon.randomthingz.client;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

//    private static final Map<ResourceLocation, CustomModelRegistryObject> customModels = new Object2ObjectOpenHashMap<>();

    /**
     * @param event a {@linkplain FMLClientSetupEvent Forge Mod Loader Client Setup Event}.
     */
    @SubscribeEvent
    public static void initialize(FMLClientSetupEvent event) {

    }

    /**
     * Todo: may be use this method.
     *
     * @param event a {@linkplain ModelRegistryEvent Model Registration Event}.
     * @see #onModelBake(ModelBakeEvent)
     */
    @Beta
    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {

    }

    /**
     * Todo: may be use this method.
     *
     * @param event a {@linkplain ModelBakeEvent Model Bake Event}.
     * @see #registerModelLoaders(ModelRegistryEvent)
     */
    @Beta
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {

    }

    /**
     * Todo: may be use custom patricles.
     *
     * @param event a {@linkplain ParticleFactoryRegisterEvent Particle Factory Registration Event}.
     */
    @Beta
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {

    }

    /**
     * Todo: may be use it, it's possibly already in use but not sure.
     *
     * @param event an {@linkplain ColorHandlerEvent.Item Item Color Handler Event} object
     */
    @Beta
    @SubscribeEvent
    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {

    }

    /**
     * Load complete event for client side.
     *
     * @param evt a {@linkplain FMLLoadCompleteEvent Forge Mod Loader Event: Load Complete} object.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Beta
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent evt) {
        EntityRenderDispatcher entityRenderManager = Minecraft.getInstance().getEntityRenderDispatcher();
        //Add our own custom armor layer to the various player renderers
        for (Entry<String, PlayerRenderer> entry : entityRenderManager.getSkinMap().entrySet()) {
            addCustomArmorLayer(entry.getValue());
        }
        // Add our own custom armor layer to everything that has an armor layer
        // Note: This includes any modded mobs that have vanilla's BipedArmorLayer added to them
        for (Entry<EntityType<?>, EntityRenderer<?>> entry : entityRenderManager.renderers.entrySet()) {
            EntityRenderer<?> renderer = entry.getValue();
            if (renderer instanceof LivingEntityRenderer) {
                addCustomArmorLayer((LivingEntityRenderer) renderer);
            }
        }
    }

    /**
     * Registers a custom armor layer.
     * Todo: Implement
     */
    @SuppressWarnings({"unused", "CommentedOutCode"})
    @Beta
    private static <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> void addCustomArmorLayer(LivingEntityRenderer<T, M> renderer) {
//        for (LayerRenderer<T, M> layerRenderer : new ArrayList<>(renderer.layerRenderers)) {
//            // Only allow an exact match, so we don't add to modded entities that only have a modded extended armor layer
//            if (layerRenderer.getClass() == BipedArmorLayer.class) {
//                BipedArmorLayer<T, M, A> bipedArmorLayer = (BipedArmorLayer<T, M, A>) layerRenderer;
//                renderer.addLayer(new RTArmorLayer<>(renderer, bipedArmorLayer.modelLeggings, bipedArmorLayer.modelArmor));
//                break;
//            }
//        }
    }

    @SuppressWarnings("unused")
    @FunctionalInterface
    public interface CustomModelRegistryObject {
        BakedModel createModel(BakedModel original, ModelBakeEvent event);
    }
}