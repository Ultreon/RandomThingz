package com.ultreon.randomthingz.client.renderer.layers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.LivingEntity;

public class RTArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public RTArmorLayer(LivingEntityRenderer<T, M> renderer, A modelLeggings, A modelArmor) {
        super(renderer, modelLeggings, modelArmor);
    }
}
