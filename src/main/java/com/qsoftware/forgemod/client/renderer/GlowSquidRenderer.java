package com.qsoftware.forgemod.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.entities.GlowSquidEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SquidModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GlowSquidRenderer extends MobRenderer<GlowSquidEntity, SquidModel<GlowSquidEntity>> {
   private static final ResourceLocation GLOW_SQUID_TEXTURES = new ResourceLocation(QForgeUtils.MOD_ID, "textures/entity/squid/glow_squid.png");

   public GlowSquidRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new SquidModel<>(), 0.7F);
   }

   /**
    * Returns the location of an entity's texture.
    */
   public @NotNull ResourceLocation getEntityTexture(@NotNull GlowSquidEntity entity) {
      return GLOW_SQUID_TEXTURES;
   }

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
    */
   protected float handleRotationFloat(GlowSquidEntity livingBase, float partialTicks) {
      return MathHelper.lerp(partialTicks, livingBase.lastTentacleAngle, livingBase.tentacleAngle);
   }
}
