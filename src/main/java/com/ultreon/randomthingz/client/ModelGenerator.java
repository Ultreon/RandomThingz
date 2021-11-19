package com.ultreon.randomthingz.client;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.tools.Toolset;
import lombok.Getter;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
// Todo: remove @Beta annotation when finished.
@Beta
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelGenerator {
    @Getter
    private static final ModelGenerator instance = new ModelGenerator();

    private final Map<ResourceLocation, IBakedModel> models = new HashMap<>();

    private ModelGenerator() {
        for (Toolset toolset : Toolset.values()) {

        }
    }

    public void addModel(IBakedModel model, ResourceLocation rl) {
        this.models.put(rl, model);
    }

    private void addToolModel(RegistryObject<? extends Item> item) {
//        ResourceLocation rl = new ResourceLocation(item.getId().getNamespace(), item.getId().getPath());
//        ItemMultiLayerBakedModel item1 = new ItemLayerModel(ImmutableList.of(new RenderMaterial(new ResourceLocation("item"), new ResourceLocation(item.getId().getNamespace(), item.getId().getPath()))));
//        addModel(item1, rl);
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        for (Map.Entry<ResourceLocation, IBakedModel> texture : instance.models.entrySet()) {
            event.getModelRegistry().put(texture.getKey(), texture.getValue());
        }
    }
}
