package com.ultreon.randomthingz.client.renderer.layers;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Free creeper charge layer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class CustomCreeperChargeLayer<T extends Creeper, M extends CreeperModel<T>> extends EnergySwirlLayer<T, M> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<T> creeperModel;

    @SuppressWarnings("unchecked")
    public CustomCreeperChargeLayer(RenderLayerParent<T, M> p_i50947_1_) {
        this(p_i50947_1_, (M) new CreeperModel<T>(2.0F));
    }

    public CustomCreeperChargeLayer(RenderLayerParent<T, M> p_i50947_1_, M creeperModel) {
        super(p_i50947_1_);
        this.creeperModel = creeperModel;
    }

    protected float xOffset(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected @NotNull ResourceLocation getTextureLocation() {
        return LIGHTNING_TEXTURE;
    }

    protected @NotNull EntityModel<T> model() {
        return this.creeperModel;
    }
}