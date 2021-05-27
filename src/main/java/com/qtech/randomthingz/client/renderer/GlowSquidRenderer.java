package com.qtech.randomthingz.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.renderer.layers.GlowSquidGlowLayer;
import com.qtech.randomthingz.entity.GlowSquidEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Glow squid entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class GlowSquidRenderer extends MobRenderer<GlowSquidEntity, SquidModel<GlowSquidEntity>> {
    /**
     * Glow squid texture.
     */
    private static final ResourceLocation GLOW_SQUID_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/squid/glow_squid.png");

    /**
     * Glow squid renderer constructor, initializer for glow squid renderer.
     *
     * @param renderManagerIn the {@linkplain EntityRendererManager entity render manager}.
     */
    public GlowSquidRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SquidModel<>(), 0.7F);

        addLayer(new GlowSquidGlowLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param entity the {@linkplain GlowSquidEntity glow squid entity}.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull GlowSquidEntity entity) {
        return GLOW_SQUID_TEXTURE;
    }

    @Override
    public void render(@NotNull GlowSquidEntity entityIn, float entityYaw, float partialTicks, @NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Method to apply rotations.
     *
     * @param entityLiving  the {@linkplain GlowSquidEntity glow squid}.
     * @param matrixStackIn the {@linkplain MatrixStack matrix stack}
     * @param ageInTicks    the {@linkplain GlowSquidEntity#ticksExisted age in ticks of the glow squid}.
     * @param rotationYaw   the {@linkplain GlowSquidEntity#rotationYaw rotation's yaw}.
     * @param partialTicks  the {@linkplain Minecraft#getRenderPartialTicks() render partial ticks}.
     */
    protected void applyRotations(GlowSquidEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, entityLiving.prevSquidPitch, entityLiving.squidPitch);
        float f1 = MathHelper.lerp(partialTicks, entityLiving.prevSquidYaw, entityLiving.squidYaw);
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
        matrixStackIn.translate(0.0D, -1.2F, 0.0D);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     *
     * @param livingBase   the {@linkplain GlowSquidEntity glow squid}.
     * @param partialTicks the {@linkplain Minecraft#getRenderPartialTicks() render partial ticks}.
     */
    protected float handleRotationFloat(GlowSquidEntity livingBase, float partialTicks) {
        return MathHelper.lerp(partialTicks, livingBase.lastTentacleAngle, livingBase.tentacleAngle);
    }
}
