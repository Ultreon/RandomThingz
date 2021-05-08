package com.qtech.randomthingz.client.renderer.layers;

import com.qtech.randomthingz.client.model.BabyCreeperModel;
import com.qtech.randomthingz.entity.baby.BabyCreeperEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class BabyCreeperChargeLayer extends EnergyLayer<BabyCreeperEntity, BabyCreeperModel> {

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final BabyCreeperModel creeperModel = new BabyCreeperModel(1);//Note: Use 1 instead of 2 for size

    public BabyCreeperChargeLayer(IEntityRenderer<BabyCreeperEntity, BabyCreeperModel> renderer) {
        super(renderer);
    }

    @Override
    protected float func_225634_a_(float modifier) {
        return modifier * 0.01F;
    }

    @Nonnull
    @Override
    protected ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    @Nonnull
    @Override
    protected EntityModel<BabyCreeperEntity> func_225635_b_() {
        return this.creeperModel;
    }
}