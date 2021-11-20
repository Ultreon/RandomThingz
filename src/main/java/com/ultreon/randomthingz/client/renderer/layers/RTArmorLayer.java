package com.ultreon.randomthingz.client.renderer.layers;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

public class RTArmorLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends BipedArmorLayer<T, M, A> {
    public RTArmorLayer(LivingRenderer<T, M> renderer, A modelLeggings, A modelArmor) {
        super(renderer, modelLeggings, modelArmor);
    }
}
