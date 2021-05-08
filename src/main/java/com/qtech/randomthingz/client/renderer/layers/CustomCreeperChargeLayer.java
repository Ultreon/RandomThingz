package com.qtech.randomthingz.client.renderer.layers;

import com.qtech.randomthingz.client.model.FreeCreeperModel;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Free creeper charge layer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class CustomCreeperChargeLayer<T extends CreeperEntity, M extends FreeCreeperModel<T>> extends EnergyLayer<T, M> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<T> creeperModel;

    @SuppressWarnings("unchecked")
    public CustomCreeperChargeLayer(IEntityRenderer<T, M> p_i50947_1_) {
        this(p_i50947_1_, (M) new FreeCreeperModel<T>(2.0F));
    }

    public CustomCreeperChargeLayer(IEntityRenderer<T, M> p_i50947_1_, M creeperModel) {
        super(p_i50947_1_);
        this.creeperModel = creeperModel;
    }

    protected float func_225634_a_(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected @NotNull ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    protected @NotNull EntityModel<T> func_225635_b_() {
        return this.creeperModel;
    }
}