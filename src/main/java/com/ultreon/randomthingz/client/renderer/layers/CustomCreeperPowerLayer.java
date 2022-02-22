package com.ultreon.randomthingz.client.renderer.layers;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
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
public class CustomCreeperPowerLayer<T extends Creeper, M extends CreeperModel<T>> extends EnergySwirlLayer<T, M> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<T> creeperModel;

    public CustomCreeperPowerLayer(RenderLayerParent<T, M> p_i50947_1_, EntityModelSet p_174472_) {
        super(p_i50947_1_);
        this.creeperModel = new CreeperModel<>(p_174472_.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    protected float xOffset(float p_225634_1_) {
        return p_225634_1_ * .01f;
    }

    protected @NotNull ResourceLocation getTextureLocation() {
        return LIGHTNING_TEXTURE;
    }

    protected @NotNull EntityModel<T> model() {
        return this.creeperModel;
    }
}