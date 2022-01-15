package com.ultreon.randomthingz.client.renderer.layers;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.GlowSquidRenderer;
import com.ultreon.randomthingz.entity.GlowSquidEntity;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Glow layer for the glow squid. Without this it's not a glow squid.
 *
 * @param <T> the {@linkplain GlowSquidEntity glow squid entity}
 */
public class GlowSquidGlowLayer<T extends GlowSquidEntity, M extends SquidModel<T>> extends EyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/squid/glow_squid_e.png"));

    /**
     * @param entityRenderer the {@linkplain GlowSquidRenderer glow squid renderer}.
     */
    public GlowSquidGlowLayer(RenderLayerParent<T, M> entityRenderer) {
        super(entityRenderer);

        if (!(entityRenderer instanceof GlowSquidRenderer)) {
            throw new IllegalArgumentException("The entity renderer must be a " + GlowSquidRenderer.class.getSimpleName());
        }
    }

    @Nonnull
    @Override
    public RenderType renderType() {
        return RENDER_TYPE;
    }
}
