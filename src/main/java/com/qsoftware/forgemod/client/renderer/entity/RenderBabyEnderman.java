package com.qsoftware.forgemod.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.client.model.entity.ModelBabyEnderman;
import com.qsoftware.forgemod.client.renderer.entity.layer.BabyEndermanEyesLayer;
import com.qsoftware.forgemod.client.renderer.entity.layer.BabyEndermanHeldBlockLayer;
import com.qsoftware.forgemod.objects.entities.baby.EntityBabyEnderman;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Copy of vanilla's enderman render, modified to use our own model/layer that is properly scaled, so that the block is held in the correct spot and the head is in the
 * proper place.
 */
public class RenderBabyEnderman extends MobRenderer<EntityBabyEnderman, ModelBabyEnderman> {

    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation("textures/entity/enderman/enderman.png");
    private final Random rnd = new Random();

    public RenderBabyEnderman(EntityRendererManager renderManager) {
        super(renderManager, new ModelBabyEnderman(), 0.5F);
        this.addLayer(new BabyEndermanEyesLayer(this));
        this.addLayer(new BabyEndermanHeldBlockLayer(this));
    }

    @Override
    public void render(EntityBabyEnderman enderman, float entityYaw, float partialTicks, @Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer renderer, int packedLightIn) {
        ModelBabyEnderman model = getEntityModel();
        model.isCarrying = enderman.getHeldBlockState() != null;
        model.isAttacking = enderman.isScreaming();
        super.render(enderman, entityYaw, partialTicks, matrix, renderer, packedLightIn);
    }

    @Nonnull
    @Override
    public Vector3d getRenderOffset(EntityBabyEnderman enderman, float partialTicks) {
        if (enderman.isScreaming()) {
            return new Vector3d(this.rnd.nextGaussian() * 0.02, 0, this.rnd.nextGaussian() * 0.02);
        }
        return super.getRenderOffset(enderman, partialTicks);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull EntityBabyEnderman enderman) {
        return ENDERMAN_TEXTURES;
    }
}