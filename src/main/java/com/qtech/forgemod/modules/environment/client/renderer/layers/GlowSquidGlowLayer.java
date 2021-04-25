package com.qtech.forgemod.modules.environment.client.renderer.layers;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.environment.client.renderer.GlowSquidRenderer;
import com.qtech.forgemod.modules.environment.entities.GlowSquidEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Glow layer for the glow squid. Without this it's not a glow squid.
 *
 * @param <T> the {@link GlowSquidEntity glow squid entity}
 */
public class GlowSquidGlowLayer<T extends GlowSquidEntity, M extends SquidModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(QForgeMod.modId, "textures/entity/squid/glow_squid_e.png"));

    /**
     * @param entityRenderer the {@link GlowSquidRenderer glow squid renderer}.
     */
    public GlowSquidGlowLayer(IEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);

        if (!(entityRenderer instanceof GlowSquidRenderer)) {
            throw new IllegalArgumentException("The entity renderer must be a " + GlowSquidRenderer.class.getSimpleName());
        }
    }

    @Nonnull
    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
