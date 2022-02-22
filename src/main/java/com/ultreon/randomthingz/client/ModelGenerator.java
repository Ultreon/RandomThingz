package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.tool.Toolset;
import lombok.Getter;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelGenerator {
    @Getter
    private static final ModelGenerator instance = new ModelGenerator();

    private final Map<ResourceLocation, BakedModel> models = new HashMap<>();

    private ModelGenerator() {

    }

    public void addModel(BakedModel model, ResourceLocation rl) {
        this.models.put(rl, model);
    }

    private void addToolModel(RegistryObject<? extends Item> item) {
//        ResourceLocation rl = new ResourceLocation(item.getId().getNamespace(), item.getId().getPath());
//        ItemMultiLayerBakedModel item1 = new ItemLayerModel(ImmutableList.of(new RenderMaterial(new ResourceLocation("item"), new ResourceLocation(item.getId().getNamespace(), item.getId().getPath()))));
//        addModel(item1, rl);
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        for (Map.Entry<ResourceLocation, BakedModel> texture : instance.models.entrySet()) {
            event.getModelRegistry().put(texture.getKey(), texture.getValue());
        }
    }
}
