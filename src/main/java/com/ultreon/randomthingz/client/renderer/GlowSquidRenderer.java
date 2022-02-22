package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.layers.GlowSquidGlowLayer;
import com.ultreon.randomthingz.entity.GlowSquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Glow squid entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class GlowSquidRenderer extends MobRenderer<GlowSquid, SquidModel<GlowSquid>> {
    /**
     * Glow squid texture.
     */
    private static final ResourceLocation GLOW_SQUID_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/squid/glow_squid.png");

    /**
     * Glow squid renderer constructor, initializer for glow squid renderer.
     *
     * @param ctx the {@linkplain EntityRendererProvider.Context entity renderer provider context}.
     */
    public GlowSquidRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SquidModel<>(ctx.bakeLayer(ModelLayers.GLOW_SQUID)), .7f);

        addLayer(new GlowSquidGlowLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     *
     * @param entity the {@linkplain GlowSquid glow squid entity}.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull GlowSquid entity) {
        return GLOW_SQUID_TEXTURE;
    }

    @Override
    public void render(@NotNull GlowSquid entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Method to apply rotations.
     *
     * @param entityLiving  the {@linkplain GlowSquid glow squid}.
     * @param matrixStackIn the {@linkplain PoseStack matrix stack}
     * @param ageInTicks    the {@linkplain GlowSquid#ticksExisted age in ticks of the glow squid}.
     * @param rotationYaw   the {@linkplain GlowSquid#rotationYaw rotation's yaw}.
     * @param partialTicks  the {@linkplain Minecraft#getRenderPartialTicks() render partial ticks}.
     */
    protected void setupRotations(GlowSquid entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = Mth.lerp(partialTicks, entityLiving.xBodyRotO, entityLiving.xBodyRot);
        float f1 = Mth.lerp(partialTicks, entityLiving.zBodyRotO, entityLiving.zBodyRot);
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180f - rotationYaw));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(f));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
        matrixStackIn.translate(0.0D, -1.2F, 0.0D);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     *
     * @param livingBase   the {@linkplain GlowSquid glow squid}.
     * @param partialTicks the {@linkplain Minecraft#getRenderPartialTicks() render partial ticks}.
     */
    protected float getBob(GlowSquid livingBase, float partialTicks) {
        return Mth.lerp(partialTicks, livingBase.oldTentacleAngle, livingBase.tentacleAngle);
    }
}
